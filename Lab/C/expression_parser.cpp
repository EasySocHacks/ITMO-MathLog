



#include "expression_parser.h"

string expression;
int position;

void getParser(string expressionArg) {
    expression = move(expressionArg);
    expression += " ";

    position = 0;
}

string nextToken() {
    for (; position < expression.length(); position++) {
        char parsingChar = expression[position];

        if (parsingChar == ' ' || parsingChar == '\n' || parsingChar == '\r') {
            continue;
        } else if ((parsingChar >= 'A' && parsingChar <= 'Z') || (parsingChar >= 'a' && parsingChar <= 'z') || parsingChar == '0') {
            string parsingString = " ";
            parsingString[0] = parsingChar;

            position++;

            return parsingString;
        } else if (parsingChar == '-' && position != expression.length() - 1 && expression[position + 1] == '>') {
            position += 2;
            return "->";
        } else if (parsingChar == '|' && position != expression.length() - 1 && expression[position + 1] == '-') {
            position += 2;
            return "|-";
        } else {
            position++;
            string ret = " ";
            ret[0] = parsingChar;

            return ret;
        }
    }

    return "";
}