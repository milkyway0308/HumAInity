package skywolf46.HumAInity.GrammerParser.Parser.Data;

public class BooleanTarget extends Targets {
    private boolean b;

    public BooleanTarget(boolean b) {
        this.b = b;
    }

    @Override
    public Object getTarget() {
        return b;
    }

    @Override
    public String getGroup() {
        return "Boolean";
    }
}
