import java.util.function.Function;

@SuppressWarnings({"unchecked", "WeakerAccess", "DuplicatedCode"})
public class TaskG extends TaskJ {
    public static Function<Integer[], Integer> SIMPLE_XOR() {
        return xs -> {
            assert (xs.length == 2);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(N(), S(Z(), U(0)))
                    ),
                    U(0),
                    S(
                            MOD(),
                            S(
                                    SUM(),
                                    S(MOD(), U(0), S(N(), S(N(), S(Z(), U(0))))),
                                    S(MOD(), U(1), S(N(), S(N(), S(Z(), U(0)))))
                            ),
                            S(N(), S(N(), S(Z(), U(0))))
                    )
            ).apply(xs);
        };
    }

    public static Function<Integer[], Integer> XOR() {
        return xs -> {
            assert (xs.length == 2);

            return R(
                    U(0),
                    S(
                            S(
                                    SUM(),
                                    S(
                                            SIMPLE_XOR(),
                                            U(0),
                                            U(1)
                                    ),
                                    S(
                                            MULT(),
                                            S(
                                                    XOR(),
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
        System.out.println(XOR().apply(new Integer[]{2, 0}));
        System.out.println(XOR().apply(new Integer[]{2, 1}));
        System.out.println(XOR().apply(new Integer[]{2, 2}));
        System.out.println(XOR().apply(new Integer[]{2, 3}));
        System.out.println(XOR().apply(new Integer[]{2, 4}));
        System.out.println(XOR().apply(new Integer[]{2, 5}));
    }
}
