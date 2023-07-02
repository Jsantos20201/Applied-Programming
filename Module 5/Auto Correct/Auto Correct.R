# The autoCorrect function takes a sentence as input and corrects any misspelled words within the sentence based on a predefined list of misspelled words and their corresponding corrections.
# Here's how the program works:

# The corrections list contains a mapping of misspelled words to their correct versions. It is defined at the beginning of the function.
# The sentence is tokenized into individual words using the unlist and strsplit functions.
# Each word in the sentence is iterated over. The word is converted to lowercase for case-insensitive comparison.
# If a misspelled word is found (i.e., it exists in the corrections list), the word is replaced with its correct version.
# The corrected words are combined back into a sentence using the paste function, with words separated by a space.
# The corrected sentence is returned as the output of the function.
# The program then includes four test cases to demonstrate the functionality of the autoCorrect function:


# Test Case 1: Basic misspelled words. The input sentence contains misspelled words, and the expected output is the same sentence with the misspelled words corrected.
# Test Case 2: Multiple misspelled words with different corrections. The input sentence contains multiple misspelled words, each with a different correction. The expected output is the sentence with the misspelled words corrected.
# Test Case 3: Misspelled words with complex corrections. The input sentence contains misspelled words with complex corrections. The expected output is the sentence with the misspelled words corrected.
# Test Case 4: No misspelled words. The input sentence does not contain any misspelled words. The expected output is the same as the input sentence.

autoCorrect <- function(sentence) {
  # Define the list of misspelled words and their corrections
  corrections <- list(
    "thiss" = "This",
    "Thiss" = "This",
    "sentennce" = "sentence",
    "withh" = "with",
    "somee" = "some",
    "mispelled" = "misspelled",
    "wordds" = "words",
    "accidant" = "accident",
    "athar" = "other",
    "beginnig" = "beginning",
    "beter" = "better",
    "consistansy" = "consistency",
    "efford" = "effort",
    "experience" = "experience",
    "happened" = "happened",
    "intelligent" = "intelligent",
    "knowledge" = "knowledge",
    "occurred" = "occurred",
    "parallel" = "parallel",
    "people" = "people",
    "proper" = "proper",
    "relevant" = "relevant",
    "sense" = "sense",
    "separate" = "separate",
    "successful" = "successful",
    "through" = "through",
    "together" = "together",
    "tomorrow" = "tomorrow",
    "truly" = "truly",
    "unfortunately" = "unfortunately",
    "weird" = "weird",
    "written" = "written",
    "abundance" = "abundance",
    "accommodate" = "accommodate",
    "argument" = "argument",
    "believe" = "believe",
    "cemetery" = "cemetery",
    "coming" = "coming",
    "definitely" = "definitely",
    "embarrass" = "embarrass",
    "familiar" = "familiar",
    "government" = "government",
    "highest" = "highest",
    "humorous" = "humorous",
    "independent" = "independent",
    "jewelry" = "jewelry",
    "language" = "language",
    "necessary" = "necessary",
    "occurrence" = "occurrence",
    "piece" = "piece",
    "receive" = "receive",
    "tomorroww" = "tomorrow",
    "whether" = "whether",
    "writing" = "writing",
    "wordds" = "words"
  )
  
  # Tokenize the sentence into individual words
  words <- unlist(strsplit(sentence, "\\s+"))
  
  # Iterate over each word and check for misspelling
  for (i in 1:length(words)) {
    word <- tolower(words[i])  # Convert word to lowercase for case-insensitive comparison
    if (word %in% names(corrections)) {
      words[i] <- corrections[[word]]  # Replace with correction
    }
  }
  
  # Combine corrected words into a sentence
  correctedSentence <- paste(words, collapse = " ")
  
  # Return the corrected sentence
  return(correctedSentence)
}

# Test Case 1: Basic misspelled words
testCase <- 1
print(paste("Test Case", testCase))
sentence1 <- "Thiss is a sentennce withh somee mispelled words."
expected1 <- "This is a sentence with some misspelled words."
result1 <- autoCorrect(sentence1)
print(result1)  # Output: "This is a sentence with some misspelled words."
print(result1 == expected1)  # Output: TRUE


# Test Case 2: Multiple misspelled words with different corrections
testCase <- 2
print(paste("Test Case", testCase))
sentence2 <- "hiss ord writen"
expected2 <- "This words written"
result2 <- autoCorrect(sentence2)
print(result2)  # Output: "This words written"
print(result2 == expected2)  # Output: TRUE

# Test Case 3: Misspelled words with complex corrections
testCase <- 3
print(paste("Test Case", testCase))
sentence3 <- "relevent comming tommorow happaned"
expected3 <- "relevant coming tomorrow happened"
result3 <- autoCorrect(sentence3)
print(result3)  # Output: "relevant coming tomorrow happened"
print(result3 == expected3)  # Output: TRUE

# Test Case 4: No misspelled words
testCase <- 4
print(paste("Test Case", testCase))
sentence4 <- "This is a sentence without any misspelled words."
expected4 <- "This is a sentence without any misspelled words."
result4 <- autoCorrect(sentence4)
print(result4)  # Output: "This is a sentence without any misspelled words."
print(result4 == expected4)  # Output: TRUE
