
#include <iostream>
#include "proof_simplifier.h"

using namespace std;

vector<string> expressionVector(0);
string line;

int main() {
    //freopen("input", "r", stdin);

    //BEGIN:ADD_AXIOMS_1-12
    line = "A->(B->A)";
    addAxiom(convertExpression(line));

    line = "(A->B)->(A->B->C)->(A->C)";
    addAxiom(convertExpression(line));

    line = "A&B->A";
    addAxiom(convertExpression(line));

    line = "A&B->B";
    addAxiom(convertExpression(line));

    line = "A->B->A&B";
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

    //END:ADD_AXIOMS_1-12 && A9

    //BEGIN:ADD_AXIOMS_A1-A8
    line = "a=b->a=c->b=c";
    addAxiomA1A8(convertExpression(line));

    line = "a=b->a'=b'";
    addAxiomA1A8(convertExpression(line));

    line = "a'=b'->a=b";
    addAxiomA1A8(convertExpression(line));

    line = "!a'=0";
    addAxiomA1A8(convertExpression(line));

    line = "a+0=a";
    addAxiomA1A8(convertExpression(line));

    line = "a+b'=(a+b)'";
    addAxiomA1A8(convertExpression(line));

    line = "a*0=0";
    addAxiomA1A8(convertExpression(line));

    line = "a*b'=a*b+a";
    addAxiomA1A8(convertExpression(line));
    //END:ADD_AXIOMS_A1-A8

    //BEGIN:SET_ZERO
    setZero();
    //END:SET_ZERO

    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    while (getline(cin, line)) {
        expressionVector.push_back(line);
    }

    simplify(expressionVector[0],
            vector<string>(expressionVector.begin() + 1, expressionVector.end()));

    return 0;
}
