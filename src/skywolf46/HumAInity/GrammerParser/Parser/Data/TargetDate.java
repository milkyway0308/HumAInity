package skywolf46.HumAInity.GrammerParser.Parser.Data;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class TargetDate extends Targets {
    private int year;
    private int month;
    private int date;

    // Year = 1980-3000
    // Month = 1-12
    // Date = 1-30
    public TargetDate(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    @Override
    public Object getTarget() {
        Calendar gregorian = new GregorianCalendar(year, month - 1, date);
        return gregorian;
    }

    @Override
    public String getGroup() {
        return "Time";
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, date);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TargetDate && dateEquals((TargetDate) obj);
    }

    private boolean dateEquals(TargetDate obj) {
        return year == obj.year && month == obj.month && date == obj.date;
    }

    @Override
    public String toString() {
        return year + "." + month + "." + date;
    }
}
