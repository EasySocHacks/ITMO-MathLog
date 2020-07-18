import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("unchecked")
public class TaskH extends TaskE{
    public static Function<Integer[], Integer> FIRST(Function<Integer[], Integer> f) {
        return xs -> {
            ArrayList<Integer> list = new ArrayList<>();
            list.addAll(Arrays.asList(xs));
            list.add(xs[xs.length - 1]);

            return (Integer) R(
                    S(
                            R(S(N(), S(N(), U(0))), U(0)),
                            U(xs.length - 1),
                            f
                    ),
                    S(
                            R(S(N(), S(N(), U(0))), U(0)),
                            S(N(), U(xs.length - 1)),
                            S(
                                    LESS(),
                                    S(
                                            R(S(N(), U(0)), U(0)),
                                            S(N(), U(xs.length - 1)),
                                            S(f, IntStream.range(0, xs.length - 1).mapToObj(i -> U(i)).collect(Collectors.toList()).toArray(Function[]::new))
                                    ),
                                    U(xs.length)
                            )
                    )).apply(xs);
        };
    }
}
