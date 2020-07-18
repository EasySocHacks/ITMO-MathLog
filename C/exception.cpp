//
// Created by Nikita on 26.06.2020.
//

#include "exception.h"

string exceptionString;
bool caused;

void setException(string exceptionArg) {
    exceptionString = move(exceptionArg);
    caused = true;
}

string getException() {
    return exceptionString;
}

bool isCaused() {
    return caused;
}

