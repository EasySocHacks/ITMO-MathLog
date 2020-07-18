import java.util.function.Function;

public class TaskA extends BaseFunctions{
    public static Function<Integer[], Integer> SUM() {
        return xs -> {
            assert (xs.length == 2);

            return xs[0] + xs[1];
        };

        /*
        return xs -> {
            assert (xs.length == 2);

            return R(U(0), S(N(), U(2))).apply(xs);
        };
         */
    }
}
