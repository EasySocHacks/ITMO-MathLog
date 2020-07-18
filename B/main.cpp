
#include <iostream>
#include "proof_simplifier.h"

using namespace std;

vector<string> expressionVector(0);
string line;

int main() {
    //freopen("input", "r", stdin);

    line = "A->(B->A)";
    addAxiom(convertExpression(line));

    line = "(A->B)->(A->B->C)->(A->C)";
    addAxiom(convertExpression(line));

    line = "A->B->A&B";
    addAxiom(convertExpression(line));

    line = "A&B->A";
    addAxiom(convertExpression(line));

    line = "A&B->B";
    addAxiom(convertExpression(line));

    line = "A->A|B";
    addAxiom(convertExpression(line));

    line = "B->A|B";
    addAxiom(convertExpression(line));

    line = "(A->C)->(B->C)->(A|B->C)";
    addAxiom(convertExpression(line));

    line = "(A->B)->(A->!B)->!A";
    addAxiom(convertExpression(line));

    line = "!(!A)->A";
    addAxiom(convertExpression(line));

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    while (getline(cin, line)) {
        expressionVector.push_back(line);
    }

    if (expressionVector.size() <= 1) {
        cout << "Proof is incorrect";

        return 0;
    }

    simplify(expressionVector[0],
            vector<string>(expressionVector.begin() + 1, expressionVector.end()));

    return 0;
}
