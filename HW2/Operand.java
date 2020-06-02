public enum Operand implements ExpressionPart {
    AND("&"),
    OR("|"),
    IMPLICATION("->"),
    NOT("!"),
    OPEN_BRACKET("("),
    CLOSE_BRACKET(")"),
    TURNSTILE("|-"),
    COMMA(",");

    private String stringValue;

    Operand(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String getStringValue() {
        return stringValue;
    }

    @Override
    public ExpressionPartType getType() {
        return ExpressionPartType.valueOf(this.toString());
    }
}
