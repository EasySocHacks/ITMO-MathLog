import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConvertedExpression {
    private ExpressionPart subExpression;

    private ConvertedExpression[] subConvertedExpressions;

    private String normalString;

    private final static Map<Integer, ConvertedExpression> map = new HashMap<>();

    public static ConvertedExpression getInstance(ExpressionPart subExpression, ConvertedExpression... subConvertedExpressions) {
/*
        if (subConvertedExpressions.length == 0) {
            ConvertedExpression convertedExpression = map.get(subExpression.hashCode());

            if (convertedExpression == null) {
                convertedExpression = new ConvertedExpression(subExpression, subConvertedExpressions);

                map.put(subExpression.hashCode(), convertedExpression);
            }

            return convertedExpression;
        } else {
            return new ConvertedExpression(subExpression, subConvertedExpressions);
        }*/

return new ConvertedExpression(subExpression, subConvertedExpressions);
    }

    private ConvertedExpression(ExpressionPart subExpression, ConvertedExpression... subConvertedExpressions) {
        this.subExpression = subExpression;
        this.subConvertedExpressions = subConvertedExpressions;

        normalString = getNormalString();
    }

    public ExpressionPart getSubExpression() {
        return subExpression;
    }

    public ConvertedExpression[] getSubConvertedExpressions() {
        return subConvertedExpressions;
    }

    public String toNormalString() {
        return normalString;
    }

    private String getNormalString() {
        if (subConvertedExpressions.length == 0) {
            return subExpression.getStringValue();
        }

        if (subExpression.getType().equals(ExpressionPartType.NOT)) {
            StringBuilder sb = new StringBuilder();

            sb.append("!");
            sb.append(subConvertedExpressions[0].toNormalString());

            return sb.toString();
        }

        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(subConvertedExpressions[0].toNormalString());
        sb.append(subExpression.getStringValue());
        sb.append(subConvertedExpressions[1].toNormalString());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConvertedExpression that = (ConvertedExpression) o;
        return subExpression.equals(that.subExpression) &&
                Arrays.equals(subConvertedExpressions, that.subConvertedExpressions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(subExpression);
        result = 31 * result + Arrays.hashCode(subConvertedExpressions);
        return result;
    }
}
