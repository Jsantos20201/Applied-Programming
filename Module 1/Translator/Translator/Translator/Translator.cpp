#include <iostream>
#include <string>
#include "Dictionary.h"

using namespace std;

int main() {
    Dictionary dictionary("dictionary.txt");
    string word;

    while (true) {
        cout << "Type an English word (q to quit): ";
        getline(cin, word);

        if (word == "q") {
            break;
        }

        string result = dictionary.findTranslation(word);
        cout << result << endl;
    }

    return 0;
}
