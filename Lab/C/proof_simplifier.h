#pragma once

#include "hypothesis_converter.h"
#include "annotation.h"
#include "axiom_checker.h"
#include <algorithm>
#include <iostream>

#ifndef CLION_PROOF_SIMPLIFIER_H
#define CLION_PROOF_SIMPLIFIER_H

void simplify(const string &hypothesis, vector<string> expressionVector);
void checkCanMP(converted_expression * expression, int expressionNumber);
#endif //CLION_PROOF_SIMPLIFIER_H
