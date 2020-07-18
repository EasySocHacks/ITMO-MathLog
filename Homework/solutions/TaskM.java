import java.util.ArrayList;
import java.util.function.Function;

public class TaskM extends TaskB {
    public static Function<Integer[], Integer> FACTORIAL() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(U(0), S(MULT(), S(N(), U(1)), U(2))),
                    S(N(), S(Z(), U(0))),
                    U(0)
            ).apply(x);
        };
    }
}
