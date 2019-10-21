package skywolf46.HumAInity.GrammerParser;

import skywolf46.HumAInity.GrammerParser.Funct.TripleFunction;
import skywolf46.HumAInity.GrammerParser.Parser.Data.Targets;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class ParseAttribute {
    private HashMap<String, HashMap<String, TripleFunction<String, String, List<Targets>, GrammerDataResultSet>>> target = new HashMap<>();
    private String currentpointer;

    public ParseAttribute at(String pointer) {
        this.currentpointer = pointer;
        return this;
    }

    public ParseAttribute add(String val, TripleFunction<String, String, List<Targets>, GrammerDataResultSet> s) {
        this.target.computeIfAbsent(currentpointer, v -> new HashMap<>()).put(val, s);
        return this;
    }

    public TripleFunction<String, String, List<Targets>, GrammerDataResultSet> get(String val) {
        return this.target.computeIfAbsent(currentpointer, v -> new HashMap<>()).get(val);
    }
}
