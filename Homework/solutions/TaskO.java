import java.util.ArrayList;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class TaskO extends TaskL {
    public static Function<Integer[], Integer> IS_PRIME() {
        return x -> {
            assert (x.length == 1);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    R(
                                            S(N(), S(Z(), U(0))),
                                            S(
                                                    R(
                                                            S(Z(), U(0)),
                                                            U(1)
                                                    ),
                                                    U(0),
                                                    U(2),
                                                    S(
                                                            EQUALS(),
                                                            S(
                                                                    SUM(),
                                                                    S(
                                                                            SUM(),
                                                                            S(
                                                                                    EQUALS(),
                                                                                    U(0),
                                                                                    S(N(), U(1))
                                                                            ),
                                                                            S(
                                                                                    EQUALS(),
                                                                                    S(N(), U(1)),
                                                                                    S(N(), S(Z(), U(0)))
                                                                            )
                                                                    ),
                                                                    S(
                                                                            HIGHER(),
                                                                            S(
                                                                                    MOD(),
                                                                                    U(0),
                                                                                    S(N(), U(1))
                                                                            ),
                                                                            S(Z(), U(0))
                                                                    )
                                                            ),
                                                            S(N(), S(Z(), U(0)))
                                                    )
                                            )
                                    ),
                                    U(0),
                                    U(0)
                            )
                    ),
                    U(0),
                    S(
                            EQUALS(),
                            S(
                                    LESS_OR_EQUALS(),
                                    U(0),
                                    S(N(), S(Z(), U(0)))
                            ),
                            S(Z(), U(0))
                    )
            ).apply(x);

            /*
            R1(0, 0) = 0
            R1(1, 1) -> R2(1, 0) = 0
            R1(2, 2) -> R2(2, 1) -> R3(2, 0) = 1

            Otherwise:
            R: y in range [0, x - 3]
            R(x, 0) =
                0 if x % 2 == 0
                1 if x % 2 != 0
            R(x, y != 0) =
                0 if x % (y + 2) == 0
                Recursive result if x % (y + 2) != 0
             */
            /*
            return S(
                    R( // R1
                            S(Z(), U(0)),
                            S(
                                    R( // R2
                                            S(Z(), U(0)),
                                            S(
                                                    R( // R3
                                                            S(N(), S(Z(), U(0))),
                                                            S(
                                                                    R(
                                                                            S(N(), S(Z(), U(0))),
                                                                            S(
                                                                                    R(
                                                                                            S(Z(), U(0)),
                                                                                            U(0)
                                                                                    ),
                                                                                    U(2),
                                                                                    S(
                                                                                            R(
                                                                                                    S(N(), S(Z(), U(0))),
                                                                                                    R(
                                                                                                            S(
                                                                                                                    MOD(),
                                                                                                                    U(0),
                                                                                                                    S(N(), S(N(), S(Z(), U(0))))
                                                                                                            ),
                                                                                                            S(
                                                                                                                    LESS(),
                                                                                                                    S(Z(), U(0)),
                                                                                                                    S(
                                                                                                                            MOD(),
                                                                                                                            U(0),
                                                                                                                            S(N(), S(N(), U(1)))
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            ),
                                                                                            U(0),
                                                                                            S(LIM_DIF_ONE(), U(1))
                                                                                    )

                                                                            )
                                                                    ),
                                                                    U(0),
                                                                    U(0)
                                                            )),
                                                    U(0),
                                                    U(1)
                                            )),
                                    U(0),
                                    U(1)
                            )),
                    U(0),
                    U(0)
            ).apply(x);
             */
        };
    }

    public static void main(String... args) {
        System.out.println(IS_PRIME().apply(new Integer[]{0}));
        System.out.println(IS_PRIME().apply(new Integer[]{1}));
        System.out.println(IS_PRIME().apply(new Integer[]{2}));
        System.out.println(IS_PRIME().apply(new Integer[]{3}));
        System.out.println(IS_PRIME().apply(new Integer[]{4}));
        System.out.println(IS_PRIME().apply(new Integer[]{5}));
        System.out.println(IS_PRIME().apply(new Integer[]{6}));
        System.out.println(IS_PRIME().apply(new Integer[]{7}));
        System.out.println(IS_PRIME().apply(new Integer[]{8}));
        System.out.println(IS_PRIME().apply(new Integer[]{9}));
    }
}
