



#include "annotation.h"

string annotation::getStringValue() const {
    switch (annotationType) {
        case AXIOM: return "Ax. sch.";
        case HYPOTHESIS: return "Hypothesis";
        case MODUS_PONENS: return "M.P.";
        default: return "";
    }
}

annotation_type annotation::getAnnotationType() const {
    return annotationType;
}

annotation::annotation(annotation_type annotationType, const vector<int> &indexes) : annotationType(annotationType),
                                                                                     indexes(indexes) {}

const vector<int> &annotation::getIndexes() const {
    return indexes;
}

annotation::annotation() {}
