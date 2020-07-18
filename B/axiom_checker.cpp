



#include "axiom_checker.h"
const int AXIOMS_NUMBER = 10;

vector<converted_expression*> axioms;

void addAxiom(converted_expression* axiom) {
    axioms.push_back(axiom);
}

int getAxiomNumber() {
    return AXIOMS_NUMBER;
}

unordered_map<int, converted_expression*> variableMap;

bool checkAxiom(converted_expression *expression, int axiomNumber) {
    variableMap.clear();

    return checkSpecificAxiom(axioms[axiomNumber], expression);
}

bool checkSpecificAxiom(converted_expression *axiom, converted_expression *expression) {
    auto *castAxiomToVariable = dynamic_cast<variable *>(axiom);

    if (castAxiomToVariable != nullptr) {

        int charValue = castAxiomToVariable->getStringValue()[0] - 'A';

        if (variableMap.count(charValue) == 0) {
            variableMap[charValue] = expression;
        } else {
            if (!variableMap[charValue]->equals(expression)) {
                return false;
            }
        }

        return true;
    }

    auto *castAxiomToUnary = dynamic_cast<unary_operation *>(axiom);

    if (castAxiomToUnary != nullptr) {
        auto *castExpressionToUnary = dynamic_cast<unary_operation *>(expression);

        if (castExpressionToUnary != nullptr) {
            return checkSpecificAxiom(
                    castAxiomToUnary->expression,
                    castExpressionToUnary->expression);
        } else {
            return false;
        }
    }

    auto *castAxiomToBinary = dynamic_cast<binary_operation *>(axiom);
    auto *castExpressionToBinary = dynamic_cast<binary_operation *>(expression);

    if (castExpressionToBinary == nullptr) {
        return false;
    }

    if (castAxiomToBinary->operation != castExpressionToBinary->operation) {
        return false;
    }

    bool isOk = checkSpecificAxiom(
            castAxiomToBinary->first,
            castExpressionToBinary->first);

    if (!isOk) {
        return false;
    }

    return checkSpecificAxiom(
            castAxiomToBinary->second,
            castExpressionToBinary->second);
}