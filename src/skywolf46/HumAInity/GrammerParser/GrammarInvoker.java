package skywolf46.HumAInity.GrammerParser;

import skywolf46.HumAInity.GrammerParser.Parser.Data.BooleanTarget;
import skywolf46.HumAInity.GrammerParser.Parser.Data.StringTarget;
import skywolf46.HumAInity.GrammerParser.Parser.Data.TargetDate;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;
import skywolf46.HumAInity.GrammerParser.Parser.IsQuestionGrammarParser;
import skywolf46.HumAInity.GrammerParser.Parser.WhenQuestionGrammarParser;

import java.util.*;
import java.util.function.Function;

public class GrammarInvoker {
    private static HashMap<Class, Function<Object, String>> msgResult = new HashMap<>();
    private static List<AbstractGrammarParser> regedParser = new ArrayList<>();

    public static <T> void addResultFunction(Class<T> returnValue, Function<T, String> res) {
        msgResult.put(returnValue, (Function<Object, String>) res);
    }

    public static void registerParser(AbstractGrammarParser gp) {
        regedParser.add(gp);
    }

    public static void init() {
        registerParser(new WhenQuestionGrammarParser());
        registerParser(new IsQuestionGrammarParser());
        addResultFunction(StringTarget.class, (s) -> {
            return s.getTarget().toString();
        });
        addResultFunction(BooleanTarget.class, (s) -> {
            if ((Boolean) s.getTarget())
                return "Yes, it is.";
            else
                return "No, it isn't.";
        });
    }

    public static String parse(ParseAttribute attr, String query) {
        for (AbstractGrammarParser gp : regedParser)
            if (gp.isCharactersAllowed(query)) {
                Targets target = gp.parseResult(attr, query, new ArrayList<>()).getFinalTargets();
                return Objects.requireNonNull(msgResult.get(target.getClass())).apply(target);
            }
        throw new IllegalStateException("Question parse failed");
    }

    public static void main(String[] args) {
        init();
        ParseAttribute attr = new ParseAttribute();
        attr.at("my")
                .add("phone", (s, cur, v) -> {
                    return new GrammerDataResultSet(new StringTarget("010-1541-1010"), s.substring(cur.length()).trim());
                })
                .at("Data")
                .add("number", (s, cur, targets) -> {
                    System.out.println("Target: " + s);
                    return new GrammerDataResultSet(new StringTarget("011-1541-1010"), s.substring(cur.length()).trim());
                }).add("halloween", (str, cur, tar) -> {
            Calendar c = Calendar.getInstance();
            Calendar current = new GregorianCalendar(c.get(Calendar.YEAR), 10, 31);
            if (current.before(c))
                current.set(Calendar.YEAR, current.get(Calendar.YEAR) + 1);
            TargetDate t = new TargetDate(current.get(Calendar.YEAR), current.get(Calendar.MONTH) + 1, current.get(Calendar.DATE));
            return new GrammerDataResultSet(t, str.substring(cur.length()).trim());
        });

        System.out.println(parse(attr, "When is 2019.9.7?"));
        System.out.println(parse(attr, "Is number equals 011-1541-1010?"));
        System.out.println(parse(attr, "When is halloween?"));
    }
}
