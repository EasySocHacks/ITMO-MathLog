public class Variable implements ExpressionPart {

    private final String stringValue;

    public Variable(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String getStringValue() {
        return stringValue;
    }

    @Override
    public ExpressionPartType getType() {
        return ExpressionPartType.VARIABLE;
    }
}
