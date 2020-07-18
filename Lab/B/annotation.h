#pragma once

#include <algorithm>
#include <vector>
#include <string>

using namespace std;

#ifndef CLION_ANNOTATION_H
#define CLION_ANNOTATION_H


enum annotation_type {
    AXIOM,
    HYPOTHESIS,
    MODUS_PONENS
};

class annotation {
public:
    annotation(annotation_type annotationType, const vector<int> &indexes);

    annotation();

private:
    annotation_type annotationType;
    vector<int> indexes;

public:
    annotation_type getAnnotationType() const;
    string getStringValue() const;
    const vector<int> &getIndexes() const;
};

#endif //CLION_ANNOTATION_H
