class Hangman {
    constructor(words) {
      this.words = words;
      this.manStages = [
        // stages omitted for brevity
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
  
    getRandomWord() {
      const randomIndex = Math.floor(Math.random() * this.words.length);
      this.selectedWord = this.words[randomIndex];
    }z
  
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
  
    isGameWon() {
        for (let char of this.selectedWord) {
            if (!this.guessedLetters.includes(char)) {
            return false;
            }
        }
        return true;
    }
  
    processGuess(letter) {
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
            if (this.incorrectGuesses >= this.maxGuesses) { // Corrected condition
              console.log("You lost the game. The word was: " + this.selectedWord);
            } else {
              console.log(this.manStages[this.incorrectGuesses]);
            }
          }
        }
      }
      
      
  
    startGame() {
      console.log("Welcome to Hangman!");
      console.log("Guess the word by entering one letter at a time.");
      console.log("You have " + this.maxGuesses + " guesses. Good luck!");
      // Display the first picture of manStages
      console.log(this.manStages[0]);
      this.getRandomWord();
      this.displayWordState();
    }
  
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
  
    play() {
      this.startGame();
      this.readUserInput();
    }
  }

// Export the Hangman class
export default Hangman; 