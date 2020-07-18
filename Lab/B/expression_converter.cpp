



#include "expression_converter.h"

string token;

converted_expression *getVariable() {
    converted_expression *ret = new variable(token);

    getNextToken();

    return ret;
}

converted_expression *getNegation() {
    if (token == const_token::NOT) {
        getNextToken();
        converted_expression *negation = getNegation();

        return new unary_operation(
                const_token::NOT,
                negation);
    }

    if (token == const_token::OPEN_BRACKET) {
        getNextToken();

        converted_expression *implication = getImplication();

        getNextToken();

        return implication;
    }

    return getVariable();
}

converted_expression *getConjunction() {
    converted_expression *ans = getNegation();

    while (token == const_token::AND) {
        getNextToken();
        converted_expression *negation = getNegation();

        ans = new binary_operation(
                const_token::AND,
                ans,
                negation);
    }

    return ans;
}

converted_expression *getDisjunction() {
    converted_expression *ans = getConjunction();

    while (token == const_token::OR) {
        getNextToken();
        converted_expression *conjunction = getConjunction();

        ans = new binary_operation(
                const_token::OR,
                ans,
                conjunction);
    }

    return  ans;
}

converted_expression *getImplication() {
    converted_expression *disjunction = getDisjunction();

    if (token == const_token::IMPLICATION) {
        getNextToken();
        converted_expression *implication = getImplication();

        return new binary_operation(
                const_token::IMPLICATION,
                disjunction,
                implication);
    }

    return disjunction;
}

converted_expression *convertExpression(string expression) {
    getParser(move(expression));

    getNextToken();

    return getImplication();
}

void getNextToken() {
    token = move(nextToken());
}