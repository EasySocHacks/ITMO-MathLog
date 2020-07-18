



#include "expression_converter.h"

string token;

converted_expression *getVariable() {
    converted_expression *ret = new variable(token);

    getNextToken();

    return ret;
}

converted_expression *getUnary() {
    if (token == const_token::FOR_ANY || token == const_token::EXISTS) {
        bool isAny = (token == const_token::FOR_ANY);
        getNextToken();

        converted_expression *term = getVariable();

        getNextToken();

        converted_expression *implication = getImplication();

        return new predicate(
                (isAny ? const_token::FOR_ANY : const_token::EXISTS),
                term,
                implication);
    }

    if (token == const_token::OPEN_BRACKET) {
        getNextToken();

        converted_expression *ans = getImplication();

        getNextToken();

        while (token == const_token::APOSTROPHE) {
            getNextToken();

            ans = new unary_operation(
                    const_token::APOSTROPHE,
                    ans);
        }

        return ans;
    }

    converted_expression *ans = getVariable();

    while (token == const_token::APOSTROPHE) {
        getNextToken();

        ans = new unary_operation(
                const_token::APOSTROPHE,
                ans);
    }

    return ans;
}

converted_expression *getMultiply() {
    converted_expression *ans = getUnary();

    while (token == const_token::MULTIPLY) {
        getNextToken();

        converted_expression *unary = getUnary();

        ans = new binary_operation(
                const_token::MULTIPLY,
                ans,
                unary);
    }

    return ans;
}

converted_expression *getSum() {
    converted_expression *ans = getMultiply();

    while (token == const_token::SUM) {
        getNextToken();

        converted_expression *multiply = getMultiply();

        ans = new binary_operation(
                const_token::SUM,
                ans,
                multiply);
    }

    return ans;
}

converted_expression *getEquals() {
    converted_expression *ans = getSum();

    if (token == const_token::EQUALS) {
        getNextToken();

        converted_expression *sum = getEquals();

        return new binary_operation(
                const_token::EQUALS,
                ans,
                sum);
    }

    return ans;
}

converted_expression *getNegation() {
    if (token == const_token::NOT) {
        getNextToken();

        return new unary_operation(
                const_token::NOT,
                getNegation());
    }

    return getEquals();
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