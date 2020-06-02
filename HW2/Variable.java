import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Variable implements ExpressionPart {

    private final String stringValue;
    private final int hash;

    private static Map<Integer, Variable> map = new HashMap<>();

    public static Variable getInstance(String stringValue) {
        int hash = stringValue.hashCode();

        Variable variable = map.get(hash);

        if (variable == null) {
            variable = new Variable(stringValue);

            map.put(hash, variable);
        }

        return variable;
    }

    private Variable(String stringValue) {
        this.stringValue = stringValue;
        hash = getHash();
    }

    private int getHash() {
        return 37 * Objects.hash(stringValue) + Objects.hashCode(getType());
    }

    @Override
    public String getStringValue() {
        return stringValue;
    }

    @Override
    public ExpressionPartType getType() {
        return ExpressionPartType.VARIABLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return stringValue.equals(variable.stringValue);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
