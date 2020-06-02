import java.util.ArrayList;
import java.util.List;

public class HypothesisConverter {
    private List<ExpressionPart> expressionPartList;
    private List<ConvertedExpression> hypothesisList;
    private ConvertedExpression provableExpression;

    HypothesisConverter(String hypothesis) {
        ExpressionParser expressionParser = new ExpressionParser(hypothesis);

        expressionPartList = expressionParser.getExpressionPartList();

        hypothesisList = new ArrayList<>();

        convert();
    }

    private void convert() {
        int lastIndex = 0;
        int currentIndex = 0;

        if (!expressionPartList.get(0).getType().equals(ExpressionPartType.TURNSTILE)) {
            while (currentIndex < expressionPartList.size()) {
                if (expressionPartList.get(currentIndex).getType().equals(ExpressionPartType.TURNSTILE) ||
                        expressionPartList.get(currentIndex).getType().equals(ExpressionPartType.COMMA)) {

                    ExpressionConverter expressionConverter = new ExpressionConverter(expressionPartList.subList(lastIndex, currentIndex));

                    hypothesisList.add(expressionConverter.getConvertedExpression());

                    lastIndex = ++currentIndex;

                    if (expressionPartList.get(currentIndex - 1).getType().equals(ExpressionPartType.TURNSTILE)) {
                        break;
                    }
                } else {
                    currentIndex++;
                }
            }
        } else {
            currentIndex++;
        }

        ExpressionConverter expressionConverter = new ExpressionConverter(expressionPartList.subList(currentIndex, expressionPartList.size()));

        provableExpression =  expressionConverter.getConvertedExpression();
    }

    public List<ConvertedExpression> getHypothesisList() {
        return hypothesisList;
    }

    public ConvertedExpression getProvableExpression() {
        return provableExpression;
    }
}
