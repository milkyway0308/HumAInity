package skywolf46.HumAInity.GrammerParser.Parser.Data;

import java.util.ArrayList;
import java.util.List;

public class ListTarget<T> extends Targets {
    private List<T> targets = new ArrayList<>();

    @Override
    public Object getTarget() {
        return targets;
    }

    @Override
    public String getGroup() {
        return "List";
    }
}
