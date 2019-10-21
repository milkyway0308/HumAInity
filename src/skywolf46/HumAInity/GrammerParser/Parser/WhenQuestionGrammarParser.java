package skywolf46.HumAInity.GrammerParser.Parser;

import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.StringTarget;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;
import skywolf46.HumAInity.GrammerParser.Parser.SubParser.NextDataParser;

import java.util.List;

// When is(!Force require) [my(MySelfTargetedInstanceChecker)] [Date Name or Time(SystemTimeOrSpecialDateParser)]|[Date Name]]
public class WhenQuestionGrammarParser extends AbstractGrammarParser {
    @Override
    public boolean isCharactersAllowed(String str) {
        return str.toLowerCase().startsWith(getGrammerTypeName());
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {
        String next = str.substring(getGrammerTypeName().length(), str.endsWith("?") ? str.length() - 1 : str.length());
        GrammerDataResultSet set = NextDataParser.INSTANCE.parseResult(attr, next, parsedBefore);
        if (!set.getFinalTargets().getGroup().equals("Time"))
            throw new IllegalStateException("Cannot parse 'When time parser' from data group '" + set.getFinalTargets().getGroup());
        return new GrammerDataResultSet(
                new StringTarget(
                        next + " is on " + set.getFinalTargets().toString() + "."
                ), str
        );
    }

    @Override
    public String getGrammerTypeName() {
        return "when is ";
    }
}
