import java.util.function.Function;

public class Task_2C extends TaskL {
    public static Function<Integer[], Integer> HEAD() {
        return x -> {
            assert (x.length == 1);

            return S(
                    PLOG(),
                    S(N(), S(N(), S(Z(), U(0)))),
                    U(0)
            ).apply(x);
        };
    }

    public static void main(String... args) {
        System.out.println(HEAD().apply(new Integer[]{2}));
        System.out.println(HEAD().apply(new Integer[]{4}));
        System.out.println(HEAD().apply(new Integer[]{12}));
        System.out.println(HEAD().apply(new Integer[]{80}));
        System.out.println(HEAD().apply(new Integer[]{128}));
    }
}
