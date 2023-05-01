#include "Dictionary.h"

Dictionary::Dictionary(string filename) : filename(filename), file(filename), locale("pt_BR.utf8") {}

string Dictionary::findTranslation(string word) {
    string line, portuguese_word, result = "";
    bool found = false;

    // Check if the word has any spaces
    if (hasSpaces(word)) {
        result = "Please type the word correctly in the dictionary.";
        return result;
    }

    // Convert the word to lowercase
    word = toLowercase(word);

    // Check if the word is a number or contains punctuation
    bool invalid_word = false;
    for (char c : word) {
        if (isdigit(c)) {
            result = "Please type in letters not numbers.";
            invalid_word = true;
            break;
        }
        else if (!isalpha(c, locale)) {
            result = "Don't type in punctuation. Only letters.";
            invalid_word = true;
            break;
        }
    }

    if (invalid_word) {
        return result;
    }

    // Search for the word in the dictionary
    found = false;
    while (getline(file, line)) {
        string lowercase_line = toLowercase(line);
        if (lowercase_line.substr(0, word.length()) == word) {
            portuguese_word = line.substr(word.length() + 1);
            result = "The Portuguese word is: " + portuguese_word;
            found = true;
            break;
        }
        else if (lowercase_line.find(word) != string::npos) {
            result = "Please type in an English word in the dictionary. Not a Portuguese word.";
            found = true;
            break;
        }
    }

    file.clear();
    file.seekg(0, ios::beg);

    if (!found) {
        result = "This word is not in the Portuguese dictionary.";
    }

    return result;
}

string Dictionary::displayPortugueseWithAccents(string portuguese_word) {
    string result = "";
    for (char c : portuguese_word) {
        switch (c) {
        case 'a':
            result += "á";
            break;
        case 'e':
            result += "é";
            break;
        case 'i':
            result += "í";
            break;
        case 'o':
            result += "ó";
            break;
        case 'u':
            result += "ú";
            break;
        case 'c':
            result += "ç";
            break;
        default:
            result += c;
            break;
        }
    }
    return result;
}


string Dictionary::toLowercase(string str) {
    string result = "";
    for (char c : str) {
        result += tolower(c);
    }
    return result;
}

bool Dictionary::hasSpaces(string word) {
    for (char c : word) {
        if (isspace(c)) {
            return true;
        }
    }
    return false;
}
