package skywolf46.HumAInity.GrammerParser.Parser;

import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;
import skywolf46.HumAInity.GrammerParser.Parser.SubParser.NextDataParser;

import java.util.List;

public class IsQuestionGrammarParser extends AbstractGrammarParser {
    @Override
    public boolean isCharactersAllowed(String str) {
        return str.toLowerCase().startsWith(getGrammerTypeName());
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {
        str = str.substring(getGrammerTypeName().length());
        if (str.endsWith("?"))
            str = str.substring(0, str.length() - 1);
        GrammerDataResultSet gds = null;
        do {
            if (str.isEmpty()) {
                if (gds == null || !gds.getFinalTargets().getGroup().equals("Boolean")) {
                    throw new IllegalStateException("Question parse failed : Is question grammar only accepts boolean return value");
                }
//                System.out.println(gds.getFinalTargets().getGroup());
//                System.out.println(gds.getFinalTargets().getTarget());
                return gds;
            }
//            System.out.println("Next index = " + str);
            gds = NextDataParser.INSTANCE.parseResult(attr, str, parsedBefore);
            str = gds.nextIndex();
        } while (true);
    }

    @Override
    public String getGrammerTypeName() {
        return "is ";
    }
}
