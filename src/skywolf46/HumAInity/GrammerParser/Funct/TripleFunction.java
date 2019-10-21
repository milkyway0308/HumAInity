package skywolf46.HumAInity.GrammerParser.Funct;

@FunctionalInterface
public interface TripleFunction<F, S, T, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R apply(F f, S s, T t);

}
