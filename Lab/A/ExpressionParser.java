import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private final String expression;
    private final List<ExpressionPart> expressionPartList;

    public ExpressionParser(String expression) {
        this.expression = expression.replaceAll(System.lineSeparator() + "|\\n|\\r|\n|\r", "") + " ";
        expressionPartList = new ArrayList<>();

        parse();
    }

    private void parse() {
        ExpressionPart parsingExpressionPart = null;

        for (int i = 0; i < expression.length(); i++) {
            char parsingChar = expression.charAt(i);

            if (parsingChar == ' ') {
                continue;
            } else if (parsingChar >= 'A' && parsingChar <= 'Z') {
                StringBuilder parsingString = new StringBuilder();

                while ((parsingChar >= 'A' && parsingChar <= 'Z') || (parsingChar >= '0' && parsingChar <= '9') || parsingChar == '\'') {
                    parsingString.append(parsingChar);

                    i++;
                    parsingChar = expression.charAt(i);
                }

                i--;


                parsingExpressionPart = new Variable(parsingString.toString());
            } else if (parsingChar == '-') {
                i++;
                parsingExpressionPart = Operand.IMPLICATION;
            } else {
                switch (parsingChar) {
                    case '&' : parsingExpressionPart = Operand.AND; break;
                    case '|' : parsingExpressionPart = Operand.OR; break;
                    case '!' : parsingExpressionPart = Operand.NOT; break;
                    case '(' : parsingExpressionPart = Operand.OPEN_BRACKET; break;
                    case ')' : parsingExpressionPart = Operand.CLOSE_BRACKET; break;
                }
            }

            expressionPartList.add(parsingExpressionPart);
        }
    }

    public List<ExpressionPart> getExpressionPartList() {
        return expressionPartList;
    }
}
