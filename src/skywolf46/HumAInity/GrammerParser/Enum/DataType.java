package skywolf46.HumAInity.GrammerParser.Enum;

public enum DataType {
    /** 참/거짓으로 이루어진 데이터. */
    BOOLEAN("Boolean"),
    /** 시간으로 나눠 떨어지는 데이터. */
    CALENDAR_DATE("Calendar"),
    /** 시간 데이터는 없고, 연/월/일 데이터로만 이루어진 데이터*/
    DATE("Date")
    ;

    private String data;
    DataType(String str){
        this.data = str;
    }

    public String getData() {
        return data;
    }
}
