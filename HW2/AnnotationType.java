public enum AnnotationType {
    AXIOM("Ax. sch."),
    HYPOTHESIS("Hypothesis"),
    MODUS_PONENS("M.P.");

    private String stringValue;

    AnnotationType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
