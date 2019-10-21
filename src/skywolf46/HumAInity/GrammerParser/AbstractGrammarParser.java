package skywolf46.HumAInity.GrammerParser;

import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.List;

public abstract class AbstractGrammarParser {

    public abstract boolean isCharactersAllowed(String str);

    public abstract GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore);

    public abstract String getGrammerTypeName();

//    public abstract int getPriority();
}
