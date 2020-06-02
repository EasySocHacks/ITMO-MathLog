import java.util.*;

public class ProofSimplifier {
    private List<ConvertedExpression> convertedHypothesisList;
    private List<ConvertedExpression> convertedExpressionList;

    private HashMap<ConvertedExpression, int[]> canMP;

    private HashMap<ConvertedExpression, Integer> convertedExpressionMap;

    private ConvertedExpression provableExpression;

    private List<Annotation> annotationList;

    private List<String> simplifiedExpressionStringList;

    public ProofSimplifier(String hypothesis, List<String> expressionList) {
        HypothesisConverter hypothesisConverter = new HypothesisConverter(hypothesis);

        convertedHypothesisList = hypothesisConverter.getHypothesisList();
        provableExpression = hypothesisConverter.getProvableExpression();

        convertedExpressionList = new ArrayList<>();

        for (String expression : expressionList) {
            ExpressionConverter expressionConverter = new ExpressionConverter(expression);

            convertedExpressionList.add(expressionConverter.getConvertedExpression());
        }

        simplifiedExpressionStringList = new ArrayList<>();
        annotationList = new ArrayList<>(Collections.nCopies(expressionList.size(), null)); //TODO: null

        convertedExpressionMap = new HashMap<>();
        canMP = new HashMap<>();

        simplify();
    }

    private void simplify() {
        checkAxiomAndHypothesis();

        for (int i = 0; i < convertedExpressionList.size(); i++) {
            ConvertedExpression expression = convertedExpressionList.get(i);

            if (!convertedExpressionMap.containsKey(expression) &&
                    canMP.containsKey(expression) &&
                    canMP.get(expression)[0] <= i) {

                annotationList.set(i, new Annotation(AnnotationType.MODUS_PONENS, canMP.get(expression)[1], canMP.get(expression)[2]));

                convertedExpressionMap.put(expression, i);
            }

            if (!convertedExpressionMap.containsKey(expression)) {
                continue;
            } else {
                if (expression.getSubExpression().getType().equals(ExpressionPartType.IMPLICATION) &&
                    convertedExpressionMap.containsKey(expression.getSubConvertedExpressions()[0]) &&
                    !canMP.containsKey(expression.getSubConvertedExpressions()[1])) {

                    canMP.put(expression.getSubConvertedExpressions()[1], new int[]{
                            Math.max(i, convertedExpressionMap.get(expression.getSubConvertedExpressions()[0])),
                            i,
                            convertedExpressionMap.get(expression.getSubConvertedExpressions()[0])
                    });
                }
            }
        }

        if (!convertedExpressionMap.containsKey(provableExpression)) {
            simplifiedExpressionStringList.add("Proof is incorrect");

            return;
        }

        List<Boolean> needExpression = new ArrayList<>(Collections.nCopies(convertedExpressionList.size(), false));
        needExpression.set(convertedExpressionMap.get(provableExpression), true);

        for (int i = convertedExpressionList.size() - 1; i >= 0; i--) {
            if (!needExpression.get(i)) {
                continue;
            }

            if (annotationList.get(i).getAnnotationType().equals(AnnotationType.MODUS_PONENS)) {
                for (int j = 0; j < annotationList.get(i).getIndexes().length; j++) {
                    needExpression.set(annotationList.get(i).getIndexes()[j], true);
                }
            }
        }

        List<Integer> numeration = new ArrayList<>();
        int index = 1;

        for (int i = 0; i < convertedExpressionList.size(); i++) {
            if (needExpression.get(i)) {
                numeration.add(index++);
            } else {
                numeration.add(-1);
            }
        }



        for (int i = convertedExpressionList.size() - 1; i >= 0; i--) {
            if (!needExpression.get(i)) {
                continue;
            }

            String expression = String.format("[%d. %s ", numeration.get(i), annotationList.get(i).getAnnotationType().getStringValue());

            for (int j = 0; j < annotationList.get(i).getIndexes().length; j++) {
                expression +=
                        (annotationList.get(i).getAnnotationType().equals(AnnotationType.MODUS_PONENS) ?
                                numeration.get(annotationList.get(i).getIndexes()[j]) :
                                annotationList.get(i).getIndexes()[j] + 1) +
                        (j == annotationList.get(i).getIndexes().length - 1 ? "" : ", ");
            }

            expression += "] ";

            expression += convertedExpressionList.get(i).toNormalString();

            simplifiedExpressionStringList.add(expression);
        }

        String hypothesis = "";

        if (convertedHypothesisList != null) {
            for (int i = 0; i < convertedHypothesisList.size(); i++) {
                hypothesis += convertedHypothesisList.get(i).toNormalString() +
                        (i == convertedHypothesisList.size() - 1 ? " " : ", ");
            }
        }

        hypothesis += "|- " + provableExpression.toNormalString();

        simplifiedExpressionStringList.add(hypothesis);

        Collections.reverse(simplifiedExpressionStringList);
    }

    private void checkAxiomAndHypothesis() {
        for (int i = 0; i < convertedExpressionList.size(); i++) {
            ConvertedExpression expression = convertedExpressionList.get(i);

            if (convertedExpressionMap.containsKey(expression)) {
                if (i == convertedExpressionList.size() - 1) {
                    convertedExpressionMap.put(expression, i);

                    annotationList.set(i, annotationList.get(convertedExpressionMap.get(expression)));
                }

                continue;
            }

            boolean deduced = false;

            if (deduced) {
                convertedExpressionMap.put(expression, i);
                continue;
            }

            if (convertedHypothesisList != null) {
                for (int j = 0; j < convertedHypothesisList.size(); j++) {
                    if (expression.equals(convertedHypothesisList.get(j))) {
                        annotationList.set(i, new Annotation(AnnotationType.HYPOTHESIS, j));

                        deduced = true;
                        break;
                    }
                }
            }

            if (deduced) {
                convertedExpressionMap.put(expression, i);
                continue;
            }

            for (int j = 0; j < Axiom.AXIOMS_NUMBER; j++) {
                if (Axiom.check(j, expression)) {
                    annotationList.set(i, new Annotation(AnnotationType.AXIOM, j));

                    deduced = true;
                    break;
                }
            }

            if (deduced) {
                convertedExpressionMap.put(expression, i);
                continue;
            }
        }
    }

    public List<String> getSimplifiedExpressionStringList() {
        return simplifiedExpressionStringList;
    }
}


