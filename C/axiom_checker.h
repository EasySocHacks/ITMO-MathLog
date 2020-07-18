#pragma once

#include "exception.h"
#include "expression_converter.h"
#include <map>
#include <unordered_map>
#include <vector>

#ifndef CLION_AXIOM_CHECKER_H
#define CLION_AXIOM_CHECKER_H

void setZero();

void addAxiom(converted_expression *axiom);
void addAxiomA1A8(converted_expression *axiom);

int getAxiomNumber();

bool checkAxiomA1A8(converted_expression *expression, int axiomNumber);
bool checkAxiom(converted_expression *expression, int axiomNumber);
bool checkSpecificAxiom(converted_expression *axiom, converted_expression *expression);

bool checkElevenAxiom(converted_expression *expression);
bool checkTwelveAxiom(converted_expression *expression);
bool checkA9Axiom(converted_expression *expression);

bool checkFreeForInsert(converted_expression *expression, converted_expression *variableExpression, bool check);

bool checkAlmostEquals(converted_expression *function, converted_expression *expression, converted_expression *variableExpression);

bool isFreeInsertion(converted_expression *expression, converted_expression *variableExpression);

#endif //CLION_AXIOM_CHECKER_H
