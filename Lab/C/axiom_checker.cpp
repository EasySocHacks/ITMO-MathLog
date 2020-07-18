



#include "axiom_checker.h"
const int AXIOMS_NUMBER = 13;

vector<converted_expression*> axioms;
vector<converted_expression*> axiomsA1A8;

map<string, int> notFreeVariable;

void addAxiom(converted_expression* axiom) {
    axioms.push_back(axiom);
}

void addAxiomA1A8(converted_expression *axiom) {
    axiomsA1A8.push_back(axiom);
}

int getAxiomNumber() {
    return AXIOMS_NUMBER;
}


unordered_map<int, converted_expression*> variableMap;

bool checkAxiomA1A8(converted_expression *expression, int axiomNumber) {
    /*
     variableMap.clear();

    return checkSpecificAxiom(axiomsA1A8[axiomNumber], expression, true);
     */

    return expression->equals(axiomsA1A8[axiomNumber]);
}

bool checkAxiom(converted_expression *expression, int axiomNumber) {
    variableMap.clear();

    if (axiomNumber >= 10) {
        if (axiomNumber == 10) {
            return checkElevenAxiom(expression);
        }

        if (axiomNumber == 11) {
            return checkTwelveAxiom(expression);
        }

        if (axiomNumber == 12) {
            return checkA9Axiom(expression);
        }
    }

    return checkSpecificAxiom(axioms[axiomNumber], expression);
}

bool checkSpecificAxiom(converted_expression *axiom, converted_expression *expression) {
    auto *castAxiomToVariable = dynamic_cast<variable *>(axiom);

    if (castAxiomToVariable != nullptr) {

        int charValue = castAxiomToVariable->getStringValue()[0];

        if (variableMap.count(charValue) == 0) {
            variableMap[charValue] = expression;

            return true;
        } else {
            if (!variableMap[charValue]->equals(expression)) {
                return false;
            }

            return true;
        }
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

    if (castExpressionToBinary == nullptr || castAxiomToBinary == nullptr) {
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

converted_expression *xValue;


bool checkElevenAxiom(converted_expression *expression) {
    auto *castBinary = dynamic_cast<binary_operation *>(expression);

    if (castBinary != nullptr) {
        auto *castPredicate = dynamic_cast<predicate *>(castBinary->first);

        if (castPredicate != nullptr && castPredicate->operation == const_token::FOR_ANY) {
            converted_expression *function = castPredicate->expression;
            converted_expression *variable = castPredicate->variableExpression;

            xValue = nullptr;

            if (!checkAlmostEquals(function, castBinary->second, variable)) {
                return false;
            }

            return checkFreeForInsert(function, variable, false);
        }

        return false;
    }

    return false;
}

bool checkTwelveAxiom(converted_expression *expression) {
    auto *castBinary = dynamic_cast<binary_operation *>(expression);

    if (castBinary != nullptr) {
        auto *castPredicate = dynamic_cast<predicate *>(castBinary->second);

        if (castPredicate != nullptr && castPredicate->operation == const_token::EXISTS) {
            converted_expression *function = castPredicate->expression;
            converted_expression *variable = castPredicate->variableExpression;

            xValue = nullptr;

            if(!checkAlmostEquals(function, castBinary->first, variable)) {
                return false;
            }

            return checkFreeForInsert(function, variable, false);
        }

        return false;
    }

    return false;
}

bool checkFreeForInsert(converted_expression *expression, converted_expression *variableExpression, bool check) {
    auto *convertVariable = dynamic_cast<variable *>(expression);

    if (convertVariable != nullptr) {
        if (!check && variableExpression->equals(convertVariable) && notFreeVariable[variableExpression->getStringValue()] == 0) {
            return checkFreeForInsert(xValue, variableExpression, true);
        }

        if (check) {
            if (notFreeVariable[convertVariable->getStringValue()] != 0) {
                setException("variable " + variableExpression->getStringValue() + " is not free for term " + xValue->getStringValue() + " in ?@-axiom.");
                return false;
            }
        }

        return true;
    }

    auto *convertPredicate = dynamic_cast<predicate *>(expression);

    if (convertPredicate != nullptr) {
        if (check) {
            return false; //TODO: ???
        }

        if (convertPredicate->variableExpression->equals(variableExpression)) {
            return true;
        }

        notFreeVariable[convertPredicate->variableExpression->getStringValue()]++;

        bool ret = checkFreeForInsert(convertPredicate->expression, variableExpression, check);

        notFreeVariable[convertPredicate->variableExpression->getStringValue()]--;

        return ret;
    }

    auto *convertUnary = dynamic_cast<unary_operation *>(expression);

    if (convertUnary != nullptr) {
        return checkFreeForInsert(convertUnary->expression, variableExpression, check);
    }

    auto *convertBinary = dynamic_cast<binary_operation *>(expression);

    if (convertBinary != nullptr) {
        bool isOk = checkFreeForInsert(convertBinary->first, variableExpression, check);

        if (!isOk) {
            return false;
        }

        return checkFreeForInsert(convertBinary->second, variableExpression, check);
    }

    return false;
}

converted_expression *zero;

void setZero() {
    string zeroString = "0";
    zero = convertExpression(zeroString);
}

bool checkA9Axiom(converted_expression *expression) {
    auto *castBinary = dynamic_cast<binary_operation *>(expression);

    if (castBinary != nullptr) {
        if (castBinary->operation == const_token::IMPLICATION) {
            converted_expression *function = castBinary->second;

            auto *otherCastToBinary = dynamic_cast<binary_operation *>(castBinary->first);

            if (otherCastToBinary == nullptr) {
                return false;
            }

            if (otherCastToBinary->operation != const_token::AND) {
                return false;
            }

            auto *castPredicate = dynamic_cast<predicate *>(otherCastToBinary->second);

            if (castPredicate == nullptr) {
                return false;
            }

            if (castPredicate->operation != const_token::FOR_ANY) {
                return false;
            }

            converted_expression *variableExpression = castPredicate->variableExpression;


            if (!otherCastToBinary->first->equals(function->insertExpression(variableExpression->getStringValue(), zero))) {
                return false;
            }

            auto *againCastBinary = dynamic_cast<binary_operation *>(castPredicate->expression);

            if (againCastBinary == nullptr) {
                return false;
            }

            if (againCastBinary->operation != const_token::IMPLICATION) {
                return false;
            }

            if (!againCastBinary->first->equals(function)) {
                return false;
            }

            if (!againCastBinary->second->equals(function->insertExpression(variableExpression->getStringValue(),
                    new unary_operation(const_token::APOSTROPHE, variableExpression)))) {
                return false;
            }

            return true;
        }

        return false;
    }

    return false;
}

bool checkAlmostEquals(converted_expression *function, converted_expression *expression, converted_expression *variableExpression) {
    auto *castFunctionToVariable = dynamic_cast<variable *>(function);

    if (castFunctionToVariable != nullptr) {
        if (function->equals(variableExpression)) {
            if (xValue == nullptr) {
                xValue = expression;
                return true;
            } else {
                return xValue->equals(expression);
            }
        } else {
            auto *castExpressionToVariable = dynamic_cast<variable *>(expression);

            if (castExpressionToVariable != nullptr) {
                return castExpressionToVariable->equals(castFunctionToVariable);
            } else {
                return false;
            }
        }
    }

    auto *castFunctionToUnary = dynamic_cast<unary_operation *>(function);

    if (castFunctionToUnary != nullptr) {
        auto *castExpressionToUnary = dynamic_cast<unary_operation *>(expression);

        if (castExpressionToUnary != nullptr) {
            if (castFunctionToUnary->operation != castExpressionToUnary->operation) {
                return false;
            }

            return checkAlmostEquals(castFunctionToUnary->expression, castExpressionToUnary->expression, variableExpression);
        }

        return false;
    }

    auto *castFunctionToPredicate = dynamic_cast<predicate *>(function);

    if (castFunctionToPredicate != nullptr) {
        auto *castExpressionToPredicate = dynamic_cast<predicate *>(expression);

        if (castExpressionToPredicate != nullptr) {
            if (castFunctionToPredicate->operation != castExpressionToPredicate->operation) {
                return false;
            }

            if (castFunctionToPredicate->variableExpression->equals(variableExpression)) {
                return function->equals(expression);
            }

            if (!castExpressionToPredicate->variableExpression->equals(castFunctionToPredicate->variableExpression)) {
                return false;
            }

            return checkAlmostEquals(castFunctionToPredicate->expression, castExpressionToPredicate->expression, variableExpression);
        }

        return false;
    }

    auto *castFunctionToBinary = dynamic_cast<binary_operation *>(function);
    auto *castExpressionToBinary = dynamic_cast<binary_operation *>(expression);

    if (castExpressionToBinary == nullptr || castFunctionToBinary == nullptr) {
        return false;
    }

    if (castFunctionToBinary->operation != castExpressionToBinary->operation) {
        return false;
    }

    bool isOk = checkAlmostEquals(castFunctionToBinary->first, castExpressionToBinary->first, variableExpression);

    if (!isOk) {
        return false;
    }

    return checkAlmostEquals(castFunctionToBinary->second, castExpressionToBinary->second, variableExpression);
}

bool isFreeInsertion(converted_expression *expression, converted_expression *variableExpression) {
    auto *convertVariable = dynamic_cast<variable *>(expression);

    if (convertVariable != nullptr) {
        if (convertVariable->equals(variableExpression)) {
            setException("variable " + variableExpression->getStringValue() + " occurs free in ?@-rule.");
            return false;
        }

        return true;
    }

    auto *convertPredicate = dynamic_cast<predicate *>(expression);

    if (convertPredicate != nullptr) {
        if (convertPredicate->variableExpression->equals(variableExpression)) {
            return true;
        }

        return isFreeInsertion(convertPredicate->expression, variableExpression);
    }

    auto *convertUnary = dynamic_cast<unary_operation *>(expression);

    if (convertUnary != nullptr) {
        return isFreeInsertion(convertUnary->expression, variableExpression);
    }

    auto *convertBinary = dynamic_cast<binary_operation *>(expression);

    if (convertBinary != nullptr) {
        bool isOk = isFreeInsertion(convertBinary->first, variableExpression);

        if (!isOk) {
            return false;
        }

        return isFreeInsertion(convertBinary->second, variableExpression);
    }

    return false;
}