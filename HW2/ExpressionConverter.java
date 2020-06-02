import java.util.List;

public class ExpressionConverter {
    private ConvertedExpression convertedExpression;
    private String convertedString;
    private List<ExpressionPart> expressionPartList;
    private int position;

    public ExpressionConverter(String expression) {
        ExpressionParser expressionParser = new ExpressionParser(expression);

        expressionPartList = expressionParser.getExpressionPartList();

        position = 0;

        convert();
    }

    public ExpressionConverter(List<ExpressionPart> expressionPartList) {
        this.expressionPartList = expressionPartList;

        position = 0;

        convert();
    }

    private void convert() {
        convertedExpression = getImplication();

        convertedString = convertToString(convertedExpression);
    }

    private String convertToString(ConvertedExpression convertedExpression) {
        if (convertedExpression.getSubConvertedExpressions() == null || convertedExpression.getSubConvertedExpressions().length == 0) {
            return convertedExpression.getSubExpression().getStringValue();
        }

        if (convertedExpression.getSubExpression().getType().equals(ExpressionPartType.NOT)) {
            StringBuilder sb = new StringBuilder();
            sb.append("(!");
            sb.append(convertToString(convertedExpression.getSubConvertedExpressions()[0]));
            sb.append(")");

            return sb.toString();
        }

        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(convertedExpression.getSubExpression().getStringValue());
        sb.append(convertToString(convertedExpression.getSubConvertedExpressions()[0]));
        sb.append(convertToString(convertedExpression.getSubConvertedExpressions()[1]));
        sb.append(")");

        return sb.toString();
    }

    private ConvertedExpression getImplication() {
        ConvertedExpression disjunction = getDisjunction();

        if (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.IMPLICATION)) {
            position++;
            ConvertedExpression implication = getImplication();

            return ConvertedExpression.getInstance(Operand.IMPLICATION, disjunction, implication);
        }

        return disjunction;
    }

    private ConvertedExpression getDisjunction() {
        ConvertedExpression conjunction = getConjunction();

        ConvertedExpression ans = conjunction;

        while (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.OR)) {
            position++;
            conjunction = getConjunction();

            ans = ConvertedExpression.getInstance(Operand.OR, ans, conjunction);
        }

        return  ans;
    }

    private ConvertedExpression getConjunction() {
        ConvertedExpression negation = getNegation();

        ConvertedExpression ans = negation;

        while (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.AND)) {
            position++;
            negation = getNegation();

            ans = ConvertedExpression.getInstance(Operand.AND, ans, negation);
        }

        return  ans;
    }

    private ConvertedExpression getNegation() {
        if (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.NOT)) {
            position++;
            ConvertedExpression negation = getNegation();

            return ConvertedExpression.getInstance(Operand.NOT, negation);
        }

        if (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.OPEN_BRACKET)) {
            position++;

            ConvertedExpression implication = getImplication();

            position++;

            return implication;
        }

        return getVariable();
    }

    private ConvertedExpression getVariable() {
        if (position >= expressionPartList.size() || !expressionPartList.get(position).getType().equals(ExpressionPartType.VARIABLE)) {
            return null;
        }

        return ConvertedExpression.getInstance(expressionPartList.get(position++));
    }

    public ConvertedExpression getConvertedExpression() {
        return convertedExpression;
    }

    public String getConvertedString() {
        return convertedString;
    }

    @Override
    public String toString() {
        return getConvertedString();
    }
}
