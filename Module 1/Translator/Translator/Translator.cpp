#include <iostream>
#include <string>
#include "Dictionary.h"

using namespace std;

int main() {
    Dictionary dictionary("dictionary.txt");

    cout << "Welcome to the English - Portuguese dictionary!" << endl;

    // Loop to allow the user to search for multiple words 
    string english_word;
    while (true) {
        cout << "Enter an English word (q to quit): ";
        cin >> english_word;

        // Check if the user wants to quit
        if (english_word == "q") {
            break;
        }

        // Translate the word
        string translation = dictionary.translate(english_word);
        cout << "Translation: " << translation << endl;
    }

    return 0;
}