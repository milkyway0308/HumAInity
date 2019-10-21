package skywolf46.HumAInity.GrammerParser.Parser.SubParser;

import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.BooleanTarget;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.List;

public class equalDataMatcher extends AbstractGrammarParser {
    @Override
    public boolean isCharactersAllowed(String str) {
        return str.startsWith(getGrammerTypeName());
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {
        if (parsedBefore.size() == 0)
            throw new IllegalStateException("IsDataMatcher cannot match data when it came first");
        // equals Matcher force break script
        Targets t = NextDataParser.INSTANCE.parseResult(attr, str.substring(getGrammerTypeName().length()), parsedBefore).getFinalTargets();
        return new GrammerDataResultSet(new BooleanTarget(t.equals(parsedBefore.get(parsedBefore.size() - 2))), "");
    }

    @Override
    public String getGrammerTypeName() {
        return "equal ";
    }
}
