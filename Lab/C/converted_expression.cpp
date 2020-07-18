#include "converted_expression.h"

variable::variable(string stringValue) : stringValue(move(stringValue)) {}

string variable::getStringValue() {
    return stringValue;
}

bool variable::equals(converted_expression *other) {
    auto *castOther = dynamic_cast<variable *>(other);

    if (castOther != nullptr) {
        return stringValue == castOther->getStringValue();
    }

    return false;
}

converted_expression *variable::insertExpression(string variable, converted_expression *insertedExpression) {
    if (variable == stringValue) {
        return insertedExpression;
    }

    return this;
}

unary_operation::unary_operation(string operation, converted_expression *expression) :
    operation(move(operation)), expression(expression) {}

string unary_operation::getStringValue() {
    if (operation == const_token::APOSTROPHE) {
        return expression->getStringValue() + const_token::APOSTROPHE;
    }

    return "(" + operation + expression->getStringValue() + ")";
}

bool unary_operation::equals(converted_expression *other) {
    auto *castOther = dynamic_cast<unary_operation *>(other);

    if (castOther != nullptr) {
        if (operation == castOther->operation) {
            return expression->equals(castOther->expression);
        }

        return false;
    }

    return  false;
}

converted_expression *unary_operation::insertExpression(string variable, converted_expression *insertedExpression) {
    return new unary_operation(
            operation,
            expression->insertExpression(move(variable), insertedExpression));
}

binary_operation::binary_operation(string operation, converted_expression *first, converted_expression *second) :
    operation(move(operation)), first(first), second(second) {}

string binary_operation::getStringValue() {
    return "(" + first->getStringValue() + operation + second->getStringValue() + ")";
}

bool binary_operation::equals(converted_expression *other) {
    auto *castOther = dynamic_cast<binary_operation *>(other);

    if (castOther != nullptr) {
        if (operation == castOther->operation) {
            if (first->equals(castOther->first)) {
                return second->equals(castOther->second);
            }

            return false;
        }

        return false;
    }

    return false;
}

converted_expression *binary_operation::insertExpression(string variable, converted_expression *insertedExpression) {
    converted_expression *newFirst = first->insertExpression(variable, insertedExpression);

    if (newFirst == nullptr) {
        return nullptr;
    }

    converted_expression *newSecond = second->insertExpression(move(variable), insertedExpression);

    if (newSecond == nullptr) {
        return nullptr;
    }

    return new binary_operation(
            operation,
            newFirst,
            newSecond);
}

predicate::predicate(string operation, converted_expression *variableExpression, converted_expression* expression) : operation(move(operation)),
                                                                                                                     variableExpression(variableExpression), expression(expression) {}

string predicate::getStringValue() {
    return "(" + operation + variableExpression->getStringValue() + "." + expression->getStringValue() + ")";
}

bool predicate::equals(converted_expression *other) {
    auto  *castPredicate = dynamic_cast<predicate *>(other);

    if (castPredicate != nullptr) {
        if (operation == castPredicate->operation) {
            if (variableExpression->equals(castPredicate->variableExpression)) {
                return expression->equals(castPredicate->expression);
            }

            return false;
        }

        return false;
    }

    return false;
}

converted_expression *predicate::insertExpression(string variable, converted_expression *insertedExpression) {
    if (variable == variableExpression->getStringValue()) {
        return this; //TODO: return nullptr?
    }

    converted_expression *newExpression = expression->insertExpression(move(variable), insertedExpression);

    if (newExpression == nullptr) {
        return nullptr;
    }

    return new predicate(
            operation,
            variableExpression,
            newExpression);
}
