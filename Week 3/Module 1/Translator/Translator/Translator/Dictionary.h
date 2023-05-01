#ifndef DICTIONARY_H
#define DICTIONARY_H

#include <fstream>
#include <string>
#include <locale>

using namespace std;

class Dictionary {
public:
    Dictionary(string filename);
    string findTranslation(string word);
    string displayPortugueseWithAccents(string portuguese_word);

private:
    ifstream file;
    string filename;
    locale locale;
    string toLowercase(string str);
    bool hasSpaces(string word);
};

#endif
