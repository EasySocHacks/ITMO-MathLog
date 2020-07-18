import java.util.function.Function;

public class TaskK extends TaskJ {
    public static Function<Integer[], Integer> POW() {
        return xs -> {
            assert (xs.length == 2);

            return (int) Math.pow(xs[0], xs[1]);
        };

        /*
        return xs -> {
            assert (xs.length == 2);

            return R(S(N(), S(Z(), U(0))), S(MULT(), U(0), U(2))).apply(xs);
        };
         */
    }
}
