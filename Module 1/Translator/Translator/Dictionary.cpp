#include "Dictionary.h"
#include <fstream> // Provides functionality for file input and output operations.
#include <locale> // Allows setting the program's locale to handle text in different languages and character encodings.
#include <algorithm> // Contains various algorithms for manipulating sequences of elements, such as searching and transforming.

using namespace std;

/**********************************************************************
 * Dictionary
 * This is the constructor of the Dictionary class. It takes a 
 * dictionaryFile parameter, which is the name of the file containing 
 * the English to Portuguese dictionary. Inside the constructor, the 
 * locale is set to Portuguese using locale::global(locale("pt_BR.UTF-8")).
 **********************************************************************/
Dictionary::Dictionary(const string& dictionaryFile) : dictionaryFile(dictionaryFile) {
    // Set the locale to Portuguese
    locale::global(locale("pt_BR.UTF-8"));
}


/**********************************************************************
 * hasSpace
 * This function checks if a given string str contains a space character. 
 * It uses the find function to search for the space character in the 
 * string and returns true if it's found, and false otherwise.
 **********************************************************************/
bool Dictionary::hasSpace(const string& str) {
    // Check if the string contains a space character
    return (str.find(' ') != string::npos);
}

/**********************************************************************
 * hasNumber
 * This function checks if a given string str contains a numeric character. 
 * It uses the any_of function from the algorithm library to iterate over 
 * each character in the string and checks if any of them are digits using 
 * ::isdigit. It returns true if at least one numeric character is found, 
 * and false otherwise.
 **********************************************************************/
bool Dictionary::hasNumber(const string& str) {
    // Check if the string contains a numeric character
    return (std::any_of(str.begin(), str.end(), ::isdigit));
}

/**********************************************************************
 * hasPunctuation
 * This function checks if a given string str contains any punctuation 
 * character. Similar to hasNumber, it uses the any_of function from the 
 * algorithm library to iterate over each character in the string and 
 * checks if any of them are punctuation using ::ispunct. It returns 
 * true if at least one punctuation character is found, and false otherwise.
 **********************************************************************/
bool Dictionary::hasPunctuation(const string& str) {
    // Check if the string contains any punctuation character
    return (std::any_of(str.begin(), str.end(), ::ispunct));
}

/**************************************************************************************************************
 * translate
 * This function translates an English word to Portuguese. 
 * It takes an englishWord parameter and returns the corresponding translation. 
 * The function opens the dictionary file specified in the constructor using 
 * ifstream. It performs the following steps:

    *It first checks if the file was opened successfully. 
     If not, it returns the error message "Error opening file".
    *It then checks if the input word contains a space, number, 
     or punctuation using the hasSpace, hasNumber, and hasPunctuation functions respectively. 
     If any of these conditions are met, it returns the corresponding error message.
    *The input word is converted to lowercase using the transform function from the algorithm library.
    *The function reads each line from the dictionary file and splits it into English 
     and Portuguese words using the colon (:) as the delimiter. It compares the lowercase 
     English word with the user's input, and if a match is found, it returns the corresponding Portuguese word.
    *If no match is found, it returns the error message "Word not found in dictionary".
 **************************************************************************************************************/
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