import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String expression = "";
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            expression += scanner.next();
        }

        ExpressionConverter expressionConverter = new ExpressionConverter(expression);

        System.out.println(expressionConverter.getConvertedString());
    }
}
