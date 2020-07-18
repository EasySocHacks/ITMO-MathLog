import java.util.ArrayList;
import java.util.function.Function;

public class TaskI extends TaskE {
    public static Function<Integer[], Integer> DIV() {

        return xs -> {
            assert (xs.length == 2);

            return xs[0] / xs[1];
        };

  /*
        return xs -> {
            assert (xs.length == 2);

            return S(
                    R(
                            S(Z(), U(0)),
                            S(
                                    R(U(0), U(1)),
                                    S(N(), U(2)),
                                    U(3),
                                    S(
                                            LESS(),
                                            U(0),
                                            S(
                                                    MULT(),
                                                    U(1),
                                                    S(N(), U(2))
                                            )
                                    )
                            )
                    ),
                    U(0),
                    U(1),
                    U(0)
            ).apply(xs);
        };
   */
    }
}
