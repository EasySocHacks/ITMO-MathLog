import java.util.function.Function;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class TaskF extends TaskJ {
    public static Function<Integer[], Integer> SIMPLE_AND() {
        return xs -> {
            assert (xs.length == 2);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(N(), S(Z(), U(0)))
                    ),
                    U(0),
                    S(
                            LIM_DIF_ONE(),
                            S(
                                    SUM(),
                                    S(MOD(), U(0), S(N(), S(N(), S(Z(), U(0))))),
                                    S(MOD(), U(1), S(N(), S(N(), S(Z(), U(0)))))
                            )
                    )
            ).apply(xs);
        };
    }

    public static Function<Integer[], Integer> LEN() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    R(
                                            U(1),
                                            S(
                                                    SUM(),
                                                    U(1),
                                                    S(N(), S(Z(), U(0)))
                                            )
                                    ),
                                    S(
                                            SUM(),
                                            U(1),
                                            S(N(), S(Z(), U(0)))
                                    ),
                                    U(2),
                                    S(
                                            EQUALS(),
                                            S(
                                                    MOD(),
                                                    S(
                                                            SUM(),
                                                            U(1),
                                                            S(N(), S(Z(), U(0)))
                                                    ),
                                                    S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(Z(), U(0))))))))))))
                                            ),
                                            S(Z(), U(0))
                                    )
                            )
                    ),
                    U(0),
                    S(
                            MULT(),
                            U(0),
                            S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(N(), S(Z(), U(0))))))))))))
                    )
            ).apply(x);
        };
    }

    public static Function<Integer[], Integer> AND() {
        return xs -> {
            assert (xs.length == 2);

            return R(
                    S(Z(), U(0)),
                    S(
                            S(
                                    SUM(),
                                    S(
                                            SIMPLE_AND(),
                                            U(0),
                                            U(1)
                                    ),
                                    S(
                                            MULT(),
                                            S(
                                                    AND(),
                                                    S(
                                                            DIV(),
                                                            U(0),
                                                            S(N(), S(N(), S(Z(), U(0))))
                                                    ),
                                                    S(
                                                            DIV(),
                                                            U(1),
                                                            S(N(), S(N(), S(Z(), U(0))))
                                                    )
                                            ),
                                            S(N(), S(N(), S(Z(), U(0))))
                                    )
                            ),
                            U(0),
                            S(N(), U(1))
                    )
            ).apply(xs);
        };
    }

    public static void main(String... args) {
        System.out.println(AND().apply(new Integer[]{3, 0}));
        System.out.println(AND().apply(new Integer[]{3, 1}));
        System.out.println(AND().apply(new Integer[]{3, 2}));
        System.out.println(AND().apply(new Integer[]{3, 3}));
        System.out.println(AND().apply(new Integer[]{3, 4}));
        System.out.println(AND().apply(new Integer[]{3, 5}));
    }
}
