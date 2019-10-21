package skywolf46.HumAInity.GrammerParser.Parser.Data;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class TargetCalendar extends Targets {
    private Calendar calc;

    public TargetCalendar(Calendar calc) {
        this.calc = calc;
    }

    @Override
    public Object getTarget() {
        return calc;
    }

    @Override
    public String getGroup() {
        return "Time";
    }

    @Override
    public int hashCode() {
        return calc.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TargetCalendar && dateEquals((TargetCalendar) obj);
    }

    private boolean dateEquals(TargetCalendar obj) {
        return obj.calc.equals(calc);
    }

    @Override
    public String toString() {
        return calc.get(Calendar.YEAR) + "." + (calc.get(Calendar.MONTH) + 1) + "." + calc.get(Calendar.DATE)
                + " " + calc.get(Calendar.HOUR_OF_DAY) + ":" + calc.get(Calendar.MINUTE) + ":" + calc.get(Calendar.SECOND);
    }
}
