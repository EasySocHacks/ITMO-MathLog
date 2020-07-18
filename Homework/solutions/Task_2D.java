import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Task_2D extends TaskO {
    public static Function<Integer[], Integer> TAIL() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    R(
                                            U(2),
                                            S(
                                                    PLOG(),
                                                    S(N(), U(1)),
                                                    U(0)
                                            )
                                    ),
                                    U(0),
                                    U(1),
                                    U(2),
                                    S(
                                            EQUALS(),
                                            S(
                                                    SUM(),
                                                    S(
                                                            EQUALS(),
                                                            S(
                                                                    MOD(),
                                                                    U(0),
                                                                    S(N(), U(1))
                                                            ),
                                                            S(Z(), U(0))
                                                    ),
                                                    S(
                                                            IS_PRIME(),
                                                            S(N(), U(1))
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
    };

    public static void main(String... args) {
        System.out.println(TAIL().apply(new Integer[]{2}));
        System.out.println(TAIL().apply(new Integer[]{4}));
        System.out.println(TAIL().apply(new Integer[]{8}));
        System.out.println(TAIL().apply(new Integer[]{9}));
        System.out.println(TAIL().apply(new Integer[]{12}));
        System.out.println(TAIL().apply(new Integer[]{18}));
    }
}
