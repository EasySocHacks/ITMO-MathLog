public class Annotation {
    private final AnnotationType annotationType;
    private final int[] indexes;

    public Annotation(AnnotationType annotationType, int... indexes) {
        this.annotationType = annotationType;
        this.indexes = indexes;
    }

    public AnnotationType getAnnotationType() {
        return annotationType;
    }

    public int[] getIndexes() {
        return indexes;
    }
}
