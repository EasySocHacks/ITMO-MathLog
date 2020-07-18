



#include "hypothesis_converter.h"

vector<converted_expression*> hypothesisVector(0);
string hypothesisString;

pair<vector<converted_expression*>, converted_expression*> convertHypothesis(const string &hypothesis) {
    hypothesisString = hypothesis + " ";
    hypothesisVector.clear();

    string str = "";

    for (int i = 0; i < hypothesisString.length(); i++) {
        if (hypothesisString[i] == ' ') {
            continue;
        }

        if (hypothesisString[i] == ',') {
            hypothesisVector.push_back(convertExpression(str));
            str = "";

            continue;
        }

        if (hypothesisString[i] == '|') {
            i++;
            if (hypothesisString[i] == '-') {
                if (str != "") {
                    hypothesisVector.push_back(convertExpression(str));
                    str = "";
                }

                continue;
            } else {
                i--;
            }
        }

        str += hypothesisString[i];
    }

    return {hypothesisVector, convertExpression(str)};
}