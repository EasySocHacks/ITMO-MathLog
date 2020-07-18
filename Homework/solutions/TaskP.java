import java.util.function.Function;

public class TaskP extends TaskO {
    public static Function<Integer[], Integer> NEXT_PRIME() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(
                            U(0),
                            S(
                                    NEXT_PRIME(),
                                    S(N(), U(0))
                            )
                    ),
                    U(0),
                    S(
                            EQUALS(),
                            S(
                                    IS_PRIME(),
                                    U(0)
                            ),
                            S(Z(), U(0))
                    )
            ).apply(x);
        };
    }

    public static Function<Integer[], Integer> PRIME_NUMBER_K_STARTING_FROM_NUMBER_N() {
        return xs -> {
            assert (xs.length == 2);

            return R(
                    S(LIM_DIF_ONE(), U(0)),
                    S(
                            PRIME_NUMBER_K_STARTING_FROM_NUMBER_N(),
                            S(
                                    SUM(),
                                    S(
                                            NEXT_PRIME(),
                                            U(0)
                                    ),
                                    S(N(), S(Z(), U(0)))
                            ),
                            U(1)
                    )
            ).apply(xs);
        };
    }

    public static Function<Integer[], Integer> PRIME_NUMBER_K() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    S(
                                            NEXT_PRIME(),
                                            U(0)
                                    ),
                                    S(N(), U(2))
                            )
                    ),
                    U(0),
                    U(0)
            ).apply(x);
        };
    }

    public static void main(String... args) {
        System.out.println(PRIME_NUMBER_K().apply(new Integer[]{1}));
        System.out.println(PRIME_NUMBER_K().apply(new Integer[]{2}));
        System.out.println(PRIME_NUMBER_K().apply(new Integer[]{3}));
        System.out.println(PRIME_NUMBER_K().apply(new Integer[]{4}));
        System.out.println(PRIME_NUMBER_K().apply(new Integer[]{5}));
    }
}
