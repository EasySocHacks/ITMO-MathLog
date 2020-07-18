#include "proof_simplifier.h"


string hypothesisStringForProofSimplifier;

converted_expression* provableExpression;

vector<converted_expression*> convertedExpressionList(0);

unordered_map<string, int> firstAppearance;

unordered_map<string, int> convertedHypothesisMap;

unordered_map<string, vector<int>> canMP;

vector<annotation*> annotationList(0);

vector<int> indexesToAnnotation(0);

void simplify(const string &hypothesis, vector<string> expressionVector) {
    //BEGIN: PREPARE
    hypothesisStringForProofSimplifier = hypothesis;

    pair<vector<converted_expression*>, converted_expression*> hypothesisPair = convertHypothesis(hypothesis);

    provableExpression = hypothesisPair.second;

    int ind = -1;
    for (converted_expression *expression : hypothesisPair.first) {
        ind++;
        convertedHypothesisMap[expression->getStringValue()] = ind;
    }

    for (string expression : expressionVector) {
        convertedExpressionList.push_back(convertExpression(expression));
    }

    annotationList.resize(expressionVector.size(), nullptr);
    //END: PREPARE

    if (!(provableExpression->equals(convertedExpressionList[convertedExpressionList.size() - 1]))) {
        cout << "Proof is incorrect";

        return;
    }

    checkAxiomAndHypothesis();

    convertedHypothesisMap.clear();

    int first;
    int second;

    string firstString;
    string secondString;

    for (int i = 0; i < convertedExpressionList.size(); i++) {
        converted_expression *expression = convertedExpressionList[i];

        if (firstAppearance[expression->getStringValue()] != i) {
            annotationList[i] = annotationList[firstAppearance[expression->getStringValue()]];
        }

        if (canMP.count(expression->getStringValue()) != 0) {
            vector<int> fromCanMP = canMP[expression->getStringValue()];

            if (firstAppearance[expression->getStringValue()] == i &&
                canMP.count(expression->getStringValue()) != 0 &&
                fromCanMP[0] < i) {

                indexesToAnnotation.clear();
                indexesToAnnotation.push_back(fromCanMP[1]);
                indexesToAnnotation.push_back(fromCanMP[2]);

                annotationList[i] = new annotation(MODUS_PONENS,
                                                   indexesToAnnotation);

                canMP.erase(expression->getStringValue());
            }
        }

        if (firstAppearance[expression->getStringValue()] == i && annotationList[i] == nullptr) {
            cout << "Proof is incorrect";

            return;
        } else {
            auto *castExpression = dynamic_cast<binary_operation *>(expression);

            if (castExpression != nullptr && castExpression->operation == const_token::IMPLICATION) {
                firstString = castExpression->first->getStringValue();

                if (firstAppearance.count(firstString) != 0) {
                    first = firstAppearance[firstString];
                    int maxx = max(i, first);

                    secondString = castExpression->second->getStringValue();
                    if (firstAppearance.count(secondString) != 0) {
                        second = firstAppearance[secondString];

                        if (second > maxx) {
                            indexesToAnnotation.clear();
                            indexesToAnnotation.push_back(maxx);
                            indexesToAnnotation.push_back(i);
                            indexesToAnnotation.push_back(first);

                            canMP[secondString] = indexesToAnnotation;
                        }
                    }
                }
            }
        }
    }

    cout << hypothesisStringForProofSimplifier << "\n";

    vector<bool> needExpression(convertedExpressionList.size(), false);
    needExpression[firstAppearance[provableExpression->getStringValue()]] =  true;

    for (int i = (int) convertedExpressionList.size() - 1; i >= 0; i--) {
        if (!needExpression[i]) {
            continue;
        }

        if (annotationList[i]->getAnnotationType() == MODUS_PONENS) {
            for (int j = 0; j < annotationList[i]->getIndexes().size(); j++) {
                needExpression[annotationList[i]->getIndexes()[j]] = true;
            }
        }
    }

    vector<int> numeration(0);
    int index = 1;

    for (int i = 0; i < convertedExpressionList.size(); i++) {
        if (needExpression[i]) {
            numeration.push_back(index++);
        } else {
            numeration.push_back(-1);
        }
    }




    for (int i = 0; i < convertedExpressionList.size(); i++) {
        if (!needExpression[i]) {
            continue;
        }


        cout << ("[");
        cout << (numeration[i]);
        cout << (". ");
        cout << (annotationList[i]->getStringValue());
        cout << (" ");

        for (int j = 0; j < annotationList[i]->getIndexes().size(); j++) {
            cout << (
                    (annotationList[i]->getAnnotationType() == MODUS_PONENS ?
                     numeration[annotationList[i]->getIndexes()[j]] :
                     annotationList[i]->getIndexes()[j] + 1));
            cout << (j == annotationList[i]->getIndexes().size() - 1 ? "" : ", ");
        }

        cout << ("] ");

        cout << (convertedExpressionList[i]->getStringValue()) << "\n";
    }
}

void checkAxiomAndHypothesis() {
    for (int i = 0; i < convertedExpressionList.size(); i++) {
        converted_expression *expression = convertedExpressionList[i];

        if (firstAppearance.count(expression->getStringValue()) == 0) {
            firstAppearance[expression->getStringValue()] = i;
        }

        bool deduced = false;

        if (convertedHypothesisMap.count(expression->getStringValue()) != 0) {
            indexesToAnnotation.clear();
            indexesToAnnotation.push_back(convertedHypothesisMap[expression->getStringValue()]);

            annotationList[i] = new annotation(HYPOTHESIS, indexesToAnnotation);

            deduced = true;
        }

        if (deduced) {
            continue;
        }


        for (int j = 0; j < getAxiomNumber(); j++) {
            if (checkAxiom(expression, j)) {
                indexesToAnnotation.clear();
                indexesToAnnotation.push_back(j);

                annotationList[i] = new annotation(AXIOM, indexesToAnnotation);

                deduced = true;
                break;
            }
        }
    }
}