package skywolf46.HumAInity.GrammerParser.Parser.SubParser;

import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.List;

public class MySelfDataParser extends AbstractGrammarParser {
    @Override
    public boolean isCharactersAllowed(String str) {
        return str.startsWith("my ");
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {

        return null;
    }

    @Override
    public String getGrammerTypeName() {
        return "my ";
    }


}
