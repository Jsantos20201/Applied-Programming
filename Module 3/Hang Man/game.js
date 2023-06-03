// Main file

// Import the Hangman class
import Hangman from './hangman.js';

// List of words for the game
const words = ['hangman', 'javascript', 'programming', 'openai', 'computer'];

// Create an instance of the Hangman class and start the game
const hangman = new Hangman(words);
hangman.play();1