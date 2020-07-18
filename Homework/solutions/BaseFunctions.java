import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseFunctions {
    public static Function<Integer[], Integer> Z() {
        return x -> {
            assert (x.length == 1);

            return 0;
        };
    }

    public static Function<Integer[], Integer> N() {
        return x -> {
            assert (x.length == 1);

            return x[0] + 1;
        };
    }

    public static Function<Integer[], Integer> U(int i) {
        return xs -> xs[i];
    }

    public static Function<Integer[], Integer> S(Function<Integer[], Integer> f, Function<Integer[], Integer>... g) {
        return xs -> f.apply(Arrays.stream(g).map(gi -> gi.apply(xs)).collect(Collectors.toList()).toArray(Integer[]::new));
    }

    public static Function<Integer[], Integer> R(Function<Integer[], Integer> f, Function<Integer[], Integer> g) {
        return xs -> {
            if (xs[xs.length - 1] == 0) {
                ArrayList<Integer> list = new ArrayList<>(Arrays.asList(xs));
                list.remove(list.size() - 1);

                return f.apply(list.toArray(Integer[]::new));
            }

            xs[xs.length - 1]--;

            Integer[] newXs =  xs.clone();

            int result = R(f, g).apply(xs);

            ArrayList<Integer> args = new ArrayList<>(Arrays.asList(newXs));
            args.add(result);

            return g.apply(args.toArray(Integer[]::new));
        };
    }

}
