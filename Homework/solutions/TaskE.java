import java.util.Arrays;
import java.util.function.Function;

public class TaskE extends TaskD {
    public static Function<Integer[], Integer> LESS() {
        return xs -> {
          assert (xs.length == 2);

          return S(R(U(0), U(1)), S(Z(), U(0)), S(N(), S(Z(), U(0))), LIM_DIF()).apply(Arrays.asList(xs[1], xs[0]).toArray(Integer[]::new));
        };
    }

    public static Function<Integer[], Integer> HIGHER() {
        return xs -> {
            assert (xs.length == 2);

            return S(
                    LESS(),
                    U(1),
                    U(0)
            ).apply(xs);
        };
    }

    public static Function<Integer[], Integer> LESS_OR_EQUALS() {
        return xs -> {
            assert (xs.length == 2);

            return S(R(S(N(), S(Z(), U(0))), S(Z(), U(0))), U(0), LESS()).apply(Arrays.asList(xs[1], xs[0]).toArray(Integer[]::new));
        };
    }

    public static Function<Integer[], Integer> EQUALS() {
        return xs -> {
            assert (xs.length == 2);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    R(
                                            S(Z(), U(0)),
                                            S(N(), S(Z(), U(0)))
                                    ),
                                    U(0),
                                    S(
                                            LESS_OR_EQUALS(),
                                            U(1),
                                            U(0)
                                    )
                            )
                    ),
                    U(0),
                    U(1),
                    S(
                            LESS_OR_EQUALS(),
                            U(0),
                            U(1)
                    )
            ).apply(xs);
        };
    }
}
