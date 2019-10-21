package skywolf46.HumAInity.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatedWordStorage {
    private static HashMap<String, RelatedWord> related = new HashMap<>();

    public static void load(File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String str;
            int related = 0;
            while ((str = br.readLine()) != null) {
                String[] split = str.split("->");
                if (split.length != 2) {
                    System.out.println("[Debug] Split length is not 2. Skipping text '" + str + "'.");
                    continue;
                }
                String[] fromSplit = split[0].split(",");
                String[] toSplit = split[1].split(",");
                related += fromSplit.length * toSplit.length;
                for (String n : fromSplit)
                    for (String n2 : toSplit) {
                        System.out.println("[Debug] Relation: " + n + "->" + n2);
                        addRelated(n2, n);
                    }
            }
            System.out.println("[Debug] Loaded " + related + " text relation");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRelated(String n, String str) {
        RelatedWord rw = related.computeIfAbsent(n, RelatedWord::new);
        RelatedWord _rw = related.computeIfAbsent(str, RelatedWord::new);
        rw.addDirectRelatedWord(str);
        _rw.addIndirectRelatedWord(n);
    }

    // Cannot over 100
    public static double getRelated(String str1, String str2, int nextIndex) {
        double precision = 0.0d;
        double currentPrecision = 50.0d;
        HashMap<Integer, Double> indirectPrecision = new HashMap<>();
        indirectPrecision.put(0, 25.d);
        int scan = 0;
        int indirectScan = 0;
        RelatedWord rw = related.computeIfAbsent(str1, RelatedWord::new);
        RelatedWord rw2 = related.computeIfAbsent(str2, RelatedWord::new);
        HashMap<Integer, List<RelatedWord>> indirectCurrentLevel = new HashMap<>();
        HashMap<Integer, List<RelatedWord>> indirectTargetLevel = new HashMap<>();
        indirectCurrentLevel.put(0, convert(rw.getIndirectRelatedInstance()));
        indirectTargetLevel.put(0, convert(rw2.getIndirectRelatedInstance()));
        for (int x = 1; x < nextIndex; x++) {
            List<RelatedWord> lr = new ArrayList<>();
            for (RelatedWord lw : indirectCurrentLevel.get(x - 1)) {
                lr.addAll(convert(lw.getIndirectRelatedInstance()));
            }
            indirectCurrentLevel.put(x, lr);
            lr = new ArrayList<>();
        }
        for (int i = 0; i < indirectTargetLevel.size(); i++) {
            indirectPrecision.put(indirectPrecision.size(), indirectPrecision.get(indirectPrecision.size() - 1) / 4);
        }
        List<RelatedWord> directCurrent = convert(rw.getDirectRelatedInstance());
        List<RelatedWord> nextDirect = new ArrayList<>();
        System.out.println("Text 01: " + str1);
        System.out.println("Text 02: " + str2);
        System.out.println("Default Indirect: " + indirectCurrentLevel);
        System.out.println("Default target: " + indirectTargetLevel);
        System.out.println("String %1 Direct:" + rw.getDirectRelatedInstance());
        System.out.println("String %1 Indirect:" + rw.getIndirectRelatedInstance());
        System.out.println("String %2 Direct:" + rw2.getDirectRelatedInstance());
        System.out.println("String %2 Indirect:" + rw2.getIndirectRelatedInstance());

        for (int x = 0; x < nextIndex; x++) {
            if (precision >= 100)
                return 100;
//            System.out.println(directCurrent);
            for (int i = 0; i < directCurrent.size(); i++) {
                if (directCurrent.get(i).getWord().equals(str2)) {
                    precision += currentPrecision + currentPrecision * (scan++ * 1.2);
                } else if (rw2.getDirectRelatedInstance().contains(directCurrent.get(i).getWord())) {
                    precision += (currentPrecision + currentPrecision * (scan++ * 1.2)) / (x + directCurrent.size());
                }
                indirectCurrentLevel.computeIfAbsent(0, n -> new ArrayList<>())
                        .addAll(convert(directCurrent.get(i).getIndirectRelatedInstance()));
                nextDirect.addAll(convert(directCurrent.get(i).getDirectRelatedInstance()));
            }
            List<Integer> key = new ArrayList<>(indirectCurrentLevel.keySet());
            // Waiting for collapse
            HashMap<Integer, List<RelatedWord>> cache = new HashMap<>();
            for (int i : key) {
                List<RelatedWord> rList = indirectCurrentLevel.get(i);
                List<RelatedWord> lr = cache.computeIfAbsent(i + 1, ax -> new ArrayList<>());
                for (RelatedWord rl : rList) {
//                    if (rl.getWord().equals(str2)) {
//                        Double indPrecision = indirectPrecision.get(i);
//                        precision += indPrecision + indPrecision * (indirectScan++ * 1.02);
//                    }
                    lr.addAll(convert(rl.getIndirectRelatedInstance()));
                }
                precision += calculateIndirectPrecision(indirectTargetLevel, indirectPrecision, rList, x);
            }
            indirectCurrentLevel.clear();
            indirectCurrentLevel = cache;
//            System.out.println("Next Indirect: " + cache);
            indirectPrecision.put(indirectPrecision.size(), indirectPrecision.get(indirectPrecision.size() - 1) / 4);
            currentPrecision /= 2;
            directCurrent = nextDirect;
            nextDirect = new ArrayList<>();
        }
        return Math.min(100, precision);
    }

    private static double calculateIndirectPrecision(HashMap<Integer, List<RelatedWord>> lw, HashMap<Integer, Double> precision, List<RelatedWord> currentList, int currentLevel) {
//        HashMap<Integer, Integer> match = new HashMap<>();
        int match = 0;
        double lastPrecision = 0d;
        for (Map.Entry<Integer, List<RelatedWord>> rw : lw.entrySet()) {
            double precisionAdding = (precision.get(rw.getKey()) + precision.get(currentLevel)) * 0.0625;
            for (RelatedWord rwl : rw.getValue())
                if (currentList.contains(rwl)) {
                    lastPrecision += precisionAdding * Math.pow(++match, 1.01);
//                    System.out.println("Adding: " + lastPrecision + "/" + match);
//                    System.out.println(rwl.getWord());
//                    match.put(rw.getKey(), match.getOrDefault(rw.getKey(), 0) + 1);
                }
        }
        return lastPrecision;
    }

    public static List<RelatedWord> convert(List<String> str) {
        List<RelatedWord> rw = new ArrayList<>();
        str.forEach(n -> rw.add(related.computeIfAbsent(n, RelatedWord::new)));
        return rw;
    }


    public static void main(String[] args) {
        load(new File("Files/PrelearningData.txt"));
        String l1 = "overwatch";
        String l2 = "enter the gungeon";
        long time = System.currentTimeMillis();
        double relate = getRelated(l1, l2, 8);
        System.out.println("[DEBUG] 데이터 처리 소요시간: " + (System.currentTimeMillis() - time) + "ms");
        System.out.println(l1 + "과 " + l2 + "가 관련되어 있을 예상 관련 수치: " + relate + "%");
        time = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            getRelated(l1, l2, 8);
//        }
        System.out.println("[DEBUG] 데이터 처리 소요시간: " + (System.currentTimeMillis() - time) + "ms");
    }
}
