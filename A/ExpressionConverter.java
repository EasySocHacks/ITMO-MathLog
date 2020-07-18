import java.util.List;

public class ExpressionConverter {
    private String convertedString;
    private List<ExpressionPart> expressionPartList;
    private int position;

    public ExpressionConverter(String expression) {
        ExpressionParser expressionParser = new ExpressionParser(expression);

        expressionPartList = expressionParser.getExpressionPartList();
        position = 0;

        convert();
    }

    private void convert() {
        convertedString = getImplication();
    }

    private String getImplication() {
        String disjunction = getDisjunction();

        if (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.IMPLICATION)) {
            position++;
            String implication = getImplication();

            return "(->," + disjunction + "," + implication + ")";
        }

        return disjunction;
    }

    private String getDisjunction() {
        String ans = "";
        String conjunction = getConjunction();

        ans = conjunction;

        while (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.OR)) {
            position++;
            conjunction = getConjunction();

            ans = "(|," + ans + "," + conjunction + ")";
        }

        return  ans;
    }

    private String getConjunction() {
        String ans = "";
        String negation = getNegation();

        ans = negation;

        while (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.AND)) {
            position++;
            negation = getNegation();

            ans = "(&," + ans + "," + negation + ")";
        }

        return  ans;
    }

    private String getNegation() {
        if (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.NOT)) {
            position++;
            String negation = getNegation();

            return "(!" + negation + ")";
        }

        if (position < expressionPartList.size() && expressionPartList.get(position).getType().equals(ExpressionPartType.OPEN_BRACKET)) {
            position++;

            String implication = getImplication();

            position++;

            return implication;
        }

        return getVariable();
    }

    private String getVariable() {
        if (position >= expressionPartList.size()) {
            return null;
        }

        return expressionPartList.get(position++).getStringValue();
    }

    public String getConvertedString() {
        return convertedString;
    }
}
