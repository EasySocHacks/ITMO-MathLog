import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Axiom {
    public static final int AXIOMS_NUMBER = 10;
    private static final ConvertedExpression axioms[] = {
            new ExpressionConverter("A->(B->A)").getConvertedExpression(),
            new ExpressionConverter("(A->B)->(A->B->C)->(A->C)").getConvertedExpression(),
            new ExpressionConverter("A->B->A&B").getConvertedExpression(),
            new ExpressionConverter("A&B->A").getConvertedExpression(),
            new ExpressionConverter("A&B->B").getConvertedExpression(),
            new ExpressionConverter("A->A|B").getConvertedExpression(),
            new ExpressionConverter("B->A|B").getConvertedExpression(),
            new ExpressionConverter("(A->C)->(B->C)->(A|B->C)").getConvertedExpression(),
            new ExpressionConverter("(A->B)->(A->!B)->!A").getConvertedExpression(),
            new ExpressionConverter("!(!A)->A").getConvertedExpression()
    };

    public static boolean check(int axiomNumber, ConvertedExpression expression) {
        Map<Integer, ConvertedExpression> map = new HashMap<>();

        return check(axioms[axiomNumber], expression, map);
    }

    private static boolean check(ConvertedExpression axiom, ConvertedExpression expression, Map<Integer, ConvertedExpression> map) {
        if (axiom.getSubConvertedExpressions() == null || axiom.getSubConvertedExpressions().length == 0) {

            int charValue = axiom.getSubExpression().getStringValue().charAt(0) - 'A';

            if (!map.containsKey(charValue)) {
                map.put(charValue, expression);
            } else {
                if (!map.get(charValue).equals(expression)) {
                    return false;
                }
            }

            return true;
        }

        if (axiom.getSubExpression().getType().equals(ExpressionPartType.NOT)) {
            if (expression.getSubExpression().getType().equals(ExpressionPartType.NOT)) {
                return check(axiom.getSubConvertedExpressions()[0], expression.getSubConvertedExpressions()[0], map);
            } else {
                return false;
            }
        }

        if (!axiom.getSubExpression().getType().equals(expression.getSubExpression().getType())) {
            return false;
        }

        boolean isOk = check(axiom.getSubConvertedExpressions()[0], expression.getSubConvertedExpressions()[0], map);

        if (!isOk) {
            return false;
        }

        return check(axiom.getSubConvertedExpressions()[1], expression.getSubConvertedExpressions()[1], map);
    }
}
