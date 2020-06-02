import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.gc();

        List<String> expressionList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.isEmpty()) {
                expressionList.add(line);
            }
        }

        ProofSimplifier proofSimplifier = new ProofSimplifier(expressionList.get(0), expressionList.subList(1, expressionList.size()));

        List<String> simplifiedExpressionList = proofSimplifier.getSimplifiedExpressionStringList();

        for (String simplifiedExpression : simplifiedExpressionList) {
            System.out.println(simplifiedExpression);
        }
    }
}
