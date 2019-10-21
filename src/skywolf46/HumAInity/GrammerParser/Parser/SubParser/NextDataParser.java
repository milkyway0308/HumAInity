package skywolf46.HumAInity.GrammerParser.Parser.SubParser;

import jdk.nashorn.internal.parser.AbstractParser;
import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.Funct.TripleFunction;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.StringTarget;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class NextDataParser extends AbstractGrammarParser {
    public static final NextDataParser INSTANCE = new NextDataParser();
    private static List<String> triggerList;
    private static HashMap<String, AbstractGrammarParser> trigger;
    private static List<AbstractGrammarParser> alwaysParser = new ArrayList<>();

    static {
        triggerList = new ArrayList<>();
        trigger = new HashMap<>();
        addTrigger(new IsDataMatcher());
        addTrigger(new equalsDataMatcher());
        addTrigger(new equalDataMatcher());
        addTrigger(new MySelfDataParser());
        addTrigger(new TimeParser());
        addTrigger(new DateParser());
    }

    public static void addTrigger(AbstractGrammarParser gp) {
        if (gp.getGrammerTypeName().isEmpty()) {
            alwaysParser.add(gp);
        } else {
            trigger.put(gp.getGrammerTypeName(), gp);
            triggerList.add(gp.getGrammerTypeName());
        }
    }

    @Override
    public boolean isCharactersAllowed(String str) {
        return true;
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {
//        System.out.println("Trigger: " + triggerList);
//        System.out.println("Data: " + str);
        for (String n : triggerList) {
//            System.out.println("Index: " + n);
            if (str.startsWith(n)) {
//                if (!n.isEmpty())
//                    System.out.println("Test: " + n);
//                String val = str.substring(n.length());
                AbstractGrammarParser agp = trigger.get(n);
//                System.out.println("Parser class: " + agp.getClass().getName());
                if (agp.isCharactersAllowed(str)) {
//                    System.out.println("AGP:" + agp.getClass().getName());
                    GrammerDataResultSet gr = agp.parseResult(attr, str, parsedBefore);
                    parsedBefore.add(gr.getFinalTargets());
                    return gr;
                }
            }
        }
        for (AbstractGrammarParser ap : alwaysParser) {
            if (ap.isCharactersAllowed(str)) {
                GrammerDataResultSet gr = ap.parseResult(attr, str, parsedBefore);
                parsedBefore.add(gr.getFinalTargets());
                return gr;
            }
        }
        int next = str.indexOf(' ');
        String target = null;
        if (next != -1)
            target = str.substring(0, next);
        else
            target = str;
//        System.out.println("Next: " + target);
        TripleFunction<String, String, List<Targets>, GrammerDataResultSet> b = attr.at("Data").get(target);
        if (target == null) {
            GrammerDataResultSet gr = new GrammerDataResultSet(new StringTarget(str), "");
            parsedBefore.add(gr.getFinalTargets());
            return gr;
        }
        if (b == null) {
            GrammerDataResultSet gr = new GrammerDataResultSet(new StringTarget(target), str.substring(next + 1));
            parsedBefore.add(gr.getFinalTargets());
            return gr;
        }
        GrammerDataResultSet gr = b.apply(str, target, parsedBefore);
        parsedBefore.add(gr.getFinalTargets());
        return gr;
    }

    @Override
    public String getGrammerTypeName() {
        return "";
    }
}
