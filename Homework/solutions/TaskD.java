import java.util.function.Function;

public class TaskD extends TaskC {
    public static Function<Integer[], Integer> LIM_DIF() {
        return xs -> {
            assert (xs.length == 2);

            return R(U(0), S(LIM_DIF_ONE(), U(2))).apply(xs);
        };
    }
}
