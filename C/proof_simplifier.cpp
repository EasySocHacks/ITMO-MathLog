#include "proof_simplifier.h"

converted_expression* provableExpression;

vector<converted_expression *> convertedExpressionList;

unordered_map<string, int> deducedExpression;

unordered_map<string, vector<int>> readyToMP;
unordered_map<string,vector<int>> waitingForMP;

vector<int> indexesToAnnotation(0);

void checkCanMP(converted_expression * expression, int expressionNumber) {
    auto *castBinary = dynamic_cast<binary_operation *>(expression);

    if (castBinary != nullptr && castBinary->operation == const_token::IMPLICATION) {
        waitingForMP[castBinary->first->getStringValue()].push_back(expressionNumber);

        if (deducedExpression.count(castBinary->first->getStringValue()) != 0) {
            if (readyToMP.count(castBinary->second->getStringValue()) == 0 ||
                readyToMP[castBinary->second->getStringValue()][1] <= deducedExpression[castBinary->first->getStringValue()]) {

                vector<int> vec(0);
                vec.push_back(expressionNumber);
                vec.push_back(deducedExpression[castBinary->first->getStringValue()]);

                readyToMP[castBinary->second->getStringValue()] = vec;
            }
        }
    }

    if (waitingForMP.count(expression->getStringValue()) != 0) {
        for (int index : waitingForMP[expression->getStringValue()]) {
            converted_expression *expressionToMP = convertedExpressionList[index];

            auto *castBinary = dynamic_cast<binary_operation *>(expressionToMP);

            if (castBinary == nullptr) {
                return;
            }

            vector<int> vec(0);
            vec.push_back(index);
            vec.push_back(expressionNumber);

            readyToMP[castBinary->second->getStringValue()] = vec;
        }
    }
}

void simplify(const string &hypothesis, vector<string> expressionVector) {
    //BEGIN: PREPARE

    pair<vector<converted_expression*>, converted_expression*> hypothesisPair = convertHypothesis(hypothesis);

    provableExpression = hypothesisPair.second;

    for (string expression : expressionVector) {
        convertedExpressionList.push_back(convertExpression(expression));
    }
    //END: PREPARE

    cout << "|-" << provableExpression->getStringValue() << "\n";

    for (int i = 0; i < expressionVector.size(); i++) {
        converted_expression *expression = convertedExpressionList[i];

        bool deduced = false;

        for (int j = 0; j < 12; j++) {
            if (checkAxiom(expression, j)) {
                cout << "[" << (i + 1) << ". " << annotation_string::AXIOM_SCH << " " << (j + 1) << "] ";
                cout << expression->getStringValue() << "\n";

                deducedExpression[expression->getStringValue()] = i;

                checkCanMP(expression, i);
                deduced = true;

                break;
            }
        }

        if (deduced) {
            continue;
        }

        for (int j = 0; j < 8; j++) {
            if (checkAxiomA1A8(expression, j)) {
                cout << "[" << (i + 1) << ". " << annotation_string::AXIOM << " A" << (j + 1) << "] ";
                cout << expression->getStringValue() << "\n";

                deducedExpression[expression->getStringValue()] = i;

                checkCanMP(expression, i);
                deduced = true;

                break;
            }
        }

        if (deduced) {
            continue;
        }

        if (checkAxiom(expression, 12)) {
            cout << "[" << (i + 1) << ". " << annotation_string::AXIOM_SCH << " A9] ";
            cout << expression->getStringValue() << "\n";

            deducedExpression[expression->getStringValue()] = i;

            checkCanMP(expression, i);

            continue;
        }

        if (readyToMP.count(expression->getStringValue()) != 0) {
            cout << "[" << (i + 1) << ". " << annotation_string::MODUS_PONENS << " ";
            cout << (readyToMP[expression->getStringValue()][1] + 1) << ", " << (readyToMP[expression->getStringValue()][0] + 1) << "] "; //TODO: SWAP?
            cout << expression->getStringValue() << "\n";

            deducedExpression[expression->getStringValue()] = i;

            checkCanMP(expression, i);

            continue;
        }

        auto *castBinary = dynamic_cast<binary_operation *>(expression);

        if (castBinary != nullptr && castBinary->operation == const_token::IMPLICATION) {
            auto *castPredicate = dynamic_cast<predicate *>(castBinary->first);

            if (castPredicate != nullptr && castPredicate->operation == const_token::EXISTS) {
                converted_expression *newExpression = new binary_operation(
                        const_token::IMPLICATION,
                        castPredicate->expression,
                        castBinary->second);

                if (deducedExpression.count(newExpression->getStringValue()) != 0) {
                    if (isFreeInsertion(castBinary->second, castPredicate->variableExpression)) {
                        cout << "[" << (i + 1) << ". " << annotation_string::INTRO << " ";
                        cout << (deducedExpression[newExpression->getStringValue()] + 1) << "] ";
                        cout << expression->getStringValue() << "\n";

                        deducedExpression[expression->getStringValue()] = i;

                        checkCanMP(expression, i);

                        continue;

                    }
                }
            }
        }

        if (castBinary != nullptr && castBinary->operation == const_token::IMPLICATION) {
            auto *castPredicate = dynamic_cast<predicate *>(castBinary->second);

            if (castPredicate != nullptr && castPredicate->operation == const_token::FOR_ANY) {
                converted_expression *newExpression = new binary_operation(
                        const_token::IMPLICATION,
                        castBinary->first,
                        castPredicate->expression);

                if (deducedExpression.count(newExpression->getStringValue()) != 0) {
                    if (isFreeInsertion(castBinary->first, castPredicate->variableExpression)) {
                        cout << "[" << (i + 1) << ". " << annotation_string::INTRO << " ";
                        cout << (deducedExpression[newExpression->getStringValue()] + 1) << "] ";
                        cout << expression->getStringValue() << "\n";

                        deducedExpression[expression->getStringValue()] = i;

                        checkCanMP(expression, i);

                        continue;
                    }
                }
            }
        }

        if (isCaused()) {
            //TODO:DELETE
            //if (expressionVector.size() != 2) exit(1);
            //TODO:DELETE

            cout << "Expression " << (i + 1) << ": ";
            cout << getException();

            return;
        } else {
            cout << "Expression " << (i + 1) << "  is not proved.";

            return;
        }
    }

    if (!convertedExpressionList.back()->equals(provableExpression)) {
        cout << "The proof proves different expression.";
    }
}