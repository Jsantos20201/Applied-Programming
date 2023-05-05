#include "Dictionary.h"
#include <fstream>
#include <locale>
#include <algorithm>

using namespace std;

Dictionary::Dictionary(const string& dictionaryFile) : dictionaryFile(dictionaryFile) {
    // Set the locale to Portuguese
    locale::global(locale("pt_BR.UTF-8"));
}

bool Dictionary::hasSpace(const string& str) {
    // Check if the string contains a space character
    return (str.find(' ') != string::npos);
}

bool Dictionary::hasNumber(const string& str) {
    // Check if the string contains a numeric character
    return (std::any_of(str.begin(), str.end(), ::isdigit));
}

bool Dictionary::hasPunctuation(const string& str) {
    // Check if the string contains any punctuation character
    return (std::any_of(str.begin(), str.end(), ::ispunct));
}

string Dictionary::translate(const string& englishWord) {
    // Open the dictionary file
    ifstream infile(dictionaryFile);

    // Check if the file was opened successfully
    if (!infile) {
        return "Error opening file";
    }

    // Check if the input contains a space
    if (hasSpace(englishWord)) {
        return "Please type in a word correctly into the dictionary.";
    }

    // Check if the input contains a number
    if (hasNumber(englishWord)) {
        return "Please don't type in numbers into the dictionary.";
    }

    // Check if the input contains punctuation
    if (hasPunctuation(englishWord)) {
        return "Please don't type in punctuation in the dictionary.";
    }

    // Convert the input to lowercase
    string lowercaseWord = englishWord;
    transform(lowercaseWord.begin(), lowercaseWord.end(), lowercaseWord.begin(), ::tolower);

    // Search for the corresponding Portuguese word in the dictionary file
    string line;
    while (getline(infile, line)) {
        // Split the line into English and Portuguese words
        size_t pos = line.find(':');
        if (pos != string::npos) {
            string e = line.substr(0, pos);
            string p = line.substr(pos + 1);

            // Convert the English word from the file to lowercase
            transform(e.begin(), e.end(), e.begin(), ::tolower);

            // Check if the English word matches the user input
            if (e == lowercaseWord) {
                // Return the Portuguese word with accents
                return p;
            }
        }
    }

    // If the word is not found in the dictionary
    return "Word not found in dictionary";
}