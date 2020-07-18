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

unary_operation::unary_operation(string operation, converted_expression *expression) :
    operation(move(operation)), expression(expression) {}

string unary_operation::getStringValue() {
    return operation + expression->getStringValue();
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
