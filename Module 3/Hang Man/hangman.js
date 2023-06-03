/**
 * Hangman Game
 *
 * This program allows the user to play the classic game of Hangman. The game randomly selects a word
 * from a predefined list of words. The user needs to guess the letters of the word within a certain
 * number of guesses. For each incorrect guess, a new body part of the hangman is displayed. If the
 * hangman is completely drawn before the word is guessed, the user loses the game. If the user guesses
 * all the letters in the word, they win the game.
 *
 * The program displays the hangman's stages using ASCII art, and keeps track of the guessed letters
 * and the remaining guesses. It also provides feedback on each guess, informing the user if it was a
 * correct or incorrect guess. The game continues until the user wins or loses, and then prompts the
 * user to play again.
 *
 * Enjoy playing Hangman!
 */

class Hangman {
    constructor(words) {
      this.words = words;
      this.manStages = [
        `
        +---+
        |   |
            |
            |
            |
            |
        =========
        `,
        `
        +---+
        |   |
        O   |
            |
            |
            |
        =========
        `,
        `
        +---+
        |   |
        O   |
        |   |
            |
            |
        =========
        `,
        `
        +---+
        |   |
        O   |
       /|   |
            |
            |
        =========
        `,
        `
        +---+
        |   |
        O   |
       /|\\  |
            |
            |
        =========
        `,
        `
        +---+
        |   |
        O   |
       /|\\  |
       /    |
            |
        =========
        `,
        `
        +---+
        |   |
        O   |
       /|\\  |
       / \\  |
            |
        =========
      `
      ];
      this.selectedWord = '';
      this.guessedLetters = [];
      this.maxGuesses = this.manStages.length - 1;
      this.incorrectGuesses = 0;
    }
  
    /**
     * getRandomWord()
     *
     * Selects a random word from the predefined list of words
     * and assigns it to the `selectedWord` property.
     */ 
    getRandomWord() {
      const randomIndex = Math.floor(Math.random() * this.words.length);
      this.selectedWord = this.words[randomIndex];
    }
  
    /**
     * displayWordState()
     *
     * Displays the current state of the word being guessed,
     * with guessed letters filled in and underscores for unknown letters.
     */
    displayWordState() {
      let wordState = '';
      for (let char of this.selectedWord) {
        if (this.guessedLetters.includes(char)) {
          wordState += char;
        } else {
          wordState += '_';
        }
        wordState += ' ';
      }
      console.log(wordState);
    }
  
    /**
     * isGameWon()
     *
     * Checks if the game has been won by comparing each letter
     * of the selected word to the guessed letters.
     *
     * @returns {boolean} True if the game is won, false otherwise.
     */
    isGameWon() {
        for (let char of this.selectedWord) {
            if (!this.guessedLetters.includes(char)) {
            return false;
            }
        }
        return true;
    }
  
    /**
     * processGuess(letter)
     *
     * Processes a user's guess by checking if the letter is already guessed,
     * updating the guessed letters list, and providing feedback on the guess.
     * If the guess is incorrect, it increments the incorrect guess count and
     * displays the corresponding hangman stage.
     *
     * @param {string} letter - The letter guessed by the user.
     */
    processGuess(letter) {
        if (!/^[a-zA-Z]$/.test(letter)) {
          console.log("Invalid input. Please enter a letter.");
          return;
        }
      
        if (this.guessedLetters.includes(letter)) {
          console.log("You already guessed that letter. Try again!");
        } else {
          this.guessedLetters.push(letter);
          if (this.selectedWord.includes(letter)) {
            console.log("Good guess!");
            if (this.isGameWon()) {
              console.log("Congratulations! You won the game!");
            }
          } else {
            console.log("Oops! Wrong guess.");
            this.incorrectGuesses++;
            console.log("Remaining guesses: " + (this.maxGuesses - this.incorrectGuesses));
            if (this.incorrectGuesses >= this.maxGuesses) {
              console.log("You lost the game. The word was: " + this.selectedWord);
            } else {
              console.log(this.manStages[this.incorrectGuesses]);
            }
          }
        }
      }

    /**
     * startGame()
     *
     * Initializes the game by displaying the welcome message,
     * selecting a random word, and displaying the initial hangman stage.
     */
    startGame() {
      console.log("Welcome to Hangman!");
      console.log("Guess the word by entering one letter at a time.");
      console.log("You have " + this.maxGuesses + " guesses. Good luck!");
      // Display the first picture of manStages
      console.log(this.manStages[0]);
      this.getRandomWord();
      this.displayWordState();
    }
  
    /**
     * readUserInput()
     *
     * Reads user input for a guess, processes the guess,
     * displays the word state, and continues reading input
     * until the game is won or lost.
     */
    readUserInput() {
      const guess = prompt("Enter your guess: ");
      const letter = guess.trim().toLowerCase();
      this.processGuess(letter);
      this.displayWordState();
  
      // Check if the game is won or lost
      if (this.isGameWon() || this.incorrectGuesses >= this.maxGuesses) {
        return;
      } else {
        this.readUserInput();
      }
    }
  
    /**
     * play()
     *
     * Starts the game by calling the startGame function
     * and then reading user input for guesses until the game
     * is won or lost.
     */
    play() {
      this.startGame();
      this.readUserInput();
    }
  }

// Export the Hangman class
export default Hangman; 