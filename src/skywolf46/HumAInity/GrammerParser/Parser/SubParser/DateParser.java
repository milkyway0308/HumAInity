package skywolf46.HumAInity.GrammerParser.Parser.SubParser;

import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.TargetDate;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser extends AbstractGrammarParser {
    private static Pattern timePattern = Pattern.compile("(19[89][0-9]|[2-9][0-9][0-9][0-9]).([1]?[0-2]|[0]?[1-9]).([12][0-9]|[3][01]|[0]?[0-9])");

    @Override
    public boolean isCharactersAllowed(String str) {
//        System.out.println("Next string : " + str);
        Matcher mt = timePattern.matcher(str);
        return mt.matches();
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {
        Matcher mt = timePattern.matcher(str);
        mt.matches();
        Targets t = new TargetDate(Integer.parseInt(mt.group(1)), Integer.parseInt(mt.group(2)), Integer.parseInt(mt.group(3)));
//        parsedBefore.add(t);
        return new GrammerDataResultSet(t,str.substring(mt.group(0).length()).trim());
    }

    @Override
    public String getGrammerTypeName() {
        return "";
    }
}
