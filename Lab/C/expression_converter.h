#pragma once

#include "expression_parser.h"

#ifndef CLION_EXPRESSION_CONVERTER_H
#define CLION_EXPRESSION_CONVERTER_H

converted_expression *getVariable();
converted_expression *getUnary();
converted_expression *getMultiply();
converted_expression *getSum();
converted_expression *getEquals();
converted_expression *getNegation();
converted_expression *getConjunction();
converted_expression *getDisjunction();
converted_expression *getImplication();

converted_expression *convertExpression(string expression);

void getNextToken();

#endif //CLION_EXPRESSION_CONVERTER_H
