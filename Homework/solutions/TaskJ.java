import java.util.function.Function;

public class TaskJ extends TaskI {
    public static Function<Integer[], Integer> MOD() {

        return xs -> xs[0] % xs[1];
/*
        return xs -> {
            assert (xs.length == 2);

            return S(LIM_DIF(), U(0), S(MULT(),  U(1), DIV())).apply(xs);
        };
 */
    }
}
