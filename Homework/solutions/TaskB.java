import java.util.function.Function;

public class TaskB extends TaskA{
    public static Function<Integer[], Integer> MULT() {
        return xs -> {
            assert (xs.length == 2);

            return xs[0] * xs[1];
        };

        /*
        return xs -> {
            assert (xs.length == 2);

            return R(S(Z(), U(0)), S(SUM(), U(0), U(2))).apply(xs);
        };
         */
    }
}
