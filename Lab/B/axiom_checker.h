#pragma once

#include "expression_converter.h"
#include <map>
#include <unordered_map>
#include <vector>

#ifndef CLION_AXIOM_CHECKER_H
#define CLION_AXIOM_CHECKER_H

void addAxiom(converted_expression *axiom);

int getAxiomNumber();

bool checkAxiom(converted_expression *expression, int axiomNumber);
bool checkSpecificAxiom(converted_expression *axiom, converted_expression *expression);

#endif //CLION_AXIOM_CHECKER_H
