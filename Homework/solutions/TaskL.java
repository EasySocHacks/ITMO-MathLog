import java.util.function.Function;

@SuppressWarnings("unchecked")
public class TaskL extends TaskK {
    public static Function<Integer[], Integer> PLOG() {

        return xs -> {
            assert (xs.length == 2);

            return S(
                    R(
                           S(Z(), U(0)),
                           S(
                                   R(
                                           U(0),
                                           U(1)
                                   ),
                                   U(3),
                                   S(N(), U(2)),
                                   S(
                                           EQUALS(),
                                           S(
                                                   MOD(),
                                                   U(1),
                                                   S(
                                                           POW(),
                                                           U(0),
                                                           S(N(), U(2))
                                                   )
                                           ),
                                           S(Z(), U(0))
                                   )
                           )
                    ),
                    U(0),
                    U(1),
                    U(1)
            ).apply(xs);
        };
    }

    public static void main(String... args) {
        System.out.println(PLOG().apply(new Integer[]{2, 1}));
        System.out.println(PLOG().apply(new Integer[]{2, 2}));
        System.out.println(PLOG().apply(new Integer[]{2, 4}));
        System.out.println(PLOG().apply(new Integer[]{2, 8}));
        System.out.println(PLOG().apply(new Integer[]{3, 11}));
        System.out.println(PLOG().apply(new Integer[]{4, 12}));
    }
}
