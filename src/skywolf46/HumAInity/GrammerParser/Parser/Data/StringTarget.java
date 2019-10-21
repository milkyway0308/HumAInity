package skywolf46.HumAInity.GrammerParser.Parser.Data;

public class StringTarget extends Targets {
    private String t;

    public StringTarget(String t) {
        this.t = t;
    }

    @Override
    public Object getTarget() {
        return t;
    }

    @Override
    public String getGroup() {
        return "Text";
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringTarget && ((StringTarget) obj).t.equals(t);
    }

    @Override
    public String toString() {
        return t;
    }
}
