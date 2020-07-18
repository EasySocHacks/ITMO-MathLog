import java.util.function.Function;

public class TaskC extends TaskB {
    public static Function<Integer[], Integer> LIM_DIF_ONE() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(U(0), U(1)),
                    S(Z(), U(0)),
                    U(0)
            ).apply(x);
        };
    }
}
