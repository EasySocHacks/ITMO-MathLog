import java.util.function.Function;

public class Task_2A extends BaseFunctions {
    public static Function<Integer[], Integer> NIL() {
        return x -> S(N(), S(Z(), U(0))).apply(x);
    }

    public static void main(String... args) {
        System.out.println(NIL().apply(new Integer[]{0}));
        System.out.println(NIL().apply(new Integer[]{1}));
        System.out.println(NIL().apply(new Integer[]{2}));
        System.out.println(NIL().apply(new Integer[]{3}));
        System.out.println(NIL().apply(new Integer[]{4}));
    }
}
