#pragma once

#include <string>

using namespace std;

#ifndef CLION_CONVERTED_EXPRESSION_H
#define CLION_CONVERTED_EXPRESSION_H

namespace const_token {
    const string IMPLICATION = "->";
    const string AND = "&";
    const string OR = "|";
    const string NOT = "!";
    const string OPEN_BRACKET = "(";
    const string TURNSTILE = "|-";
    const string COMMA = ",";
}

class converted_expression {
public:
    virtual string getStringValue() = 0;
    virtual bool equals(converted_expression *other) = 0;
};

class variable : public converted_expression {
public:
    string stringValue;
    explicit variable(string stringValue);

    string getStringValue() override;
    bool equals(converted_expression *other) override;
};

class unary_operation : public converted_expression{
public:
    string operation;
    converted_expression *expression;
    unary_operation(string operation, converted_expression *expression);

    string getStringValue() override;
    bool equals(converted_expression *other) override;
};

class binary_operation : public converted_expression {
public:
    string operation;
    converted_expression *first, *second;
    binary_operation(string operation, converted_expression *first, converted_expression *second);

    string getStringValue() override;
    bool equals(converted_expression *other) override;
};

#endif //CLION_CONVERTED_EXPRESSION_H
