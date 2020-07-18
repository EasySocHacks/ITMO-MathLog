import java.util.Random;

public class Main extends TaskE {
    public static void main(String... args) {
        System.out.println(HIGHER().apply(new Integer[]{2, 1}));
        System.out.println(HIGHER().apply(new Integer[]{1, 2}));
        System.out.println(HIGHER().apply(new Integer[]{2, 2}));
        System.out.println(HIGHER().apply(new Integer[]{1, 0}));
        System.out.println(HIGHER().apply(new Integer[]{0, 1}));
        System.out.println(HIGHER().apply(new Integer[]{0, 0}));
    }
}
