#ifndef DICTIONARY_H
#define DICTIONARY_H

#include <string>

using namespace std;

class Dictionary {
public:
    Dictionary(const string& dictionaryFile);

    string translate(const string& englishWord);

private:
    string dictionaryFile;

    bool hasSpace(const string& str);
    bool hasNumber(const string& str);
    bool hasPunctuation(const string& str);
};

#endif  // DICTIONARY_H