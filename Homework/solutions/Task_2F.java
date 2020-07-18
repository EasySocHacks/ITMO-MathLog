import java.util.function.Function;

public class Task_2F extends TaskO {
    public static Function<Integer[], Integer> LEN() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    R(
                                            U(0),
                                            S(N(), U(0))
                                    ),
                                    U(2),
                                    S(
                                            EQUALS(),
                                            S(
                                                    SUM(),
                                                    S(
                                                            IS_PRIME(),
                                                            S(N(), U(1))
                                                    ),
                                                    S(
                                                            EQUALS(),
                                                            S(
                                                                    MOD(),
                                                                    U(0),
                                                                    S(N(), U(1))
                                                            ),
                                                            S(Z(), U(0))
                                                    )
                                            ),
                                            S(N(), S(N(), S(Z(), U(0))))
                                    )
                            )
                    ),
                    U(0),
                    U(0)
            ).apply(x);
        };
    }

    public static void main(String... args) {
        System.out.println(LEN().apply(new Integer[]{2}));
        System.out.println(LEN().apply(new Integer[]{4}));
        System.out.println(LEN().apply(new Integer[]{8}));
        System.out.println(LEN().apply(new Integer[]{6}));
        System.out.println(LEN().apply(new Integer[]{16}));
        System.out.println(LEN().apply(new Integer[]{18}));
    }
}
