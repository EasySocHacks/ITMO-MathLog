import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private final String expression;
    private final List<ExpressionPart> expressionPartList;

    public ExpressionParser(String expression) {
        this.expression = expression + " ";
        expressionPartList = new ArrayList<>();

        parse();
    }

    private void parse() {
        ExpressionPart parsingExpressionPart = null;

        for (int i = 0; i < expression.length(); i++) {
            char parsingChar = expression.charAt(i);

            if (parsingChar == ' ' || parsingChar == '\n' || parsingChar == '\r') {
                continue;
            } else if (parsingChar >= 'A' && parsingChar <= 'Z') {
                StringBuilder parsingString = new StringBuilder();

                while ((parsingChar >= 'A' && parsingChar <= 'Z') || (parsingChar >= '0' && parsingChar <= '9') || parsingChar == '\'') {
                    parsingString.append(parsingChar);

                    i++;
                    parsingChar = expression.charAt(i);
                }

                i--;


                parsingExpressionPart = Variable.getInstance(parsingString.toString());
            } else if (parsingChar == '-' && i != expression.length() - 1 && expression.charAt(i + 1) == '>') {
                i++;
                parsingExpressionPart = Operand.IMPLICATION;
            } else if (parsingChar == '|' && i != expression.length() - 1 && expression.charAt(i + 1) == '-') {
                i++;
                parsingExpressionPart = Operand.TURNSTILE;
            } else {
                switch (parsingChar) {
                    case '&' : parsingExpressionPart = Operand.AND; break;
                    case '|' : parsingExpressionPart = Operand.OR; break;
                    case '!' : parsingExpressionPart = Operand.NOT; break;
                    case '(' : parsingExpressionPart = Operand.OPEN_BRACKET; break;
                    case ')' : parsingExpressionPart = Operand.CLOSE_BRACKET; break;
                    case ',' : parsingExpressionPart = Operand.COMMA; break;
                }
            }

            expressionPartList.add(parsingExpressionPart);
        }
    }

    public List<ExpressionPart> getExpressionPartList() {
        return expressionPartList;
    }
}
