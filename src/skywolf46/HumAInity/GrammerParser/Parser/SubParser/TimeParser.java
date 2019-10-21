package skywolf46.HumAInity.GrammerParser.Parser.SubParser;

import skywolf46.HumAInity.GrammerParser.AbstractGrammarParser;
import skywolf46.HumAInity.GrammerParser.GrammerDataResultSet;
import skywolf46.HumAInity.GrammerParser.ParseAttribute;
import skywolf46.HumAInity.GrammerParser.Parser.Data.TargetCalendar;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser extends AbstractGrammarParser {
    private static Pattern timePattern = Pattern.compile("(\\d+){4,6}.([1]?[0-2]|[0]?[1-9]).([12][0-9]|[3][01]|[0]?[0-9]) (1[0-9]|2[0-4]|[0]?[0-9]):([1-5][0-9]|[0]?[0-9]):([1-5][0-9]|[0]?[0-9])");

    @Override
    public boolean isCharactersAllowed(String str) {
        Matcher mt = timePattern.matcher(str);
        return mt.matches();
    }

    @Override
    public GrammerDataResultSet parseResult(ParseAttribute attr, String str, List<Targets> parsedBefore) {
        Matcher mt = timePattern.matcher(str);
        mt.matches();
        GregorianCalendar calc = new GregorianCalendar();
        calc.set(Calendar.YEAR, Integer.parseInt(mt.group(1)));
        calc.set(Calendar.MONTH, Integer.parseInt(mt.group(2)));
        calc.set(Calendar.DATE, Integer.parseInt(mt.group(3)));
        calc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mt.group(4)));
        calc.set(Calendar.MINUTE, Integer.parseInt(mt.group(5)));
        calc.set(Calendar.SECOND, Integer.parseInt(mt.group(6)));
        calc.set(Calendar.MILLISECOND, 0);
        Targets t = new TargetCalendar(calc);
//        parsedBefore.add(t);
        return new GrammerDataResultSet(t, str.substring(mt.group(0).length()).trim());
    }

    @Override
    public String getGrammerTypeName() {
        return "";
    }
}
