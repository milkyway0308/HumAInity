package skywolf46.HumAInity.GrammerParser;

import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.ArrayList;
import java.util.List;

public class GrammerDataResultSet {
    private Targets finalTargets;
    private String next;

    public GrammerDataResultSet(Targets rt, String next) {
        this.finalTargets = rt;
        this.next = next;
    }

    public Targets getFinalTargets() {
        return finalTargets;
    }

    public String nextIndex() {
        return next;
    }
}
