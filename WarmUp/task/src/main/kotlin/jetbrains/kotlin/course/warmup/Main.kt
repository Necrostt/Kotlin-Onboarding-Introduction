package jetbrains.kotlin.course.warmup

fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."


fun countExactMatches(secret: String, guess: String): Int {
    var count = 0
    //Lambda function: index (Int - Index of list), symbol (Char - Character in said index of list) -> guess[index] (takes same index used in secret to guess) == symbol (compares character of both lists thar are in the same index)
    val result = secret.filterIndexed { index, symbol -> guess[index] == symbol}
    val resultCount = result.length
    return resultCount
}
fun countAllMatches(secret: String, guess: String):Int{
    // it = element in secret list, starting from index 0
    val resultSecret = secret.filter { it in guess }
    val resultGuess = guess.filter { it in secret }
    val result = minOf(resultSecret.length, resultGuess.length)
    return result
}
fun countPartialMatches(secret: String, guess: String): Int {
    val result = countAllMatches(secret, guess) - countExactMatches(secret, guess)
    return result
}

fun generateSecret(): String = "ABCD"

fun isComplete(secret: String, guess: String): Boolean = secret.uppercase() == guess.uppercase()

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int){
    var complete: Boolean
    var attempts = 0
    var fullMatch: Int
    var partialMatch: Int
       do {
           attempts++
           println("Please input your guess. It should be of length $wordLength.")
           val guess = safeReadLine()
           complete = isComplete(secret, guess)
           fullMatch = countExactMatches(secret, guess)
           partialMatch = countPartialMatches(secret, guess)
            println("Your guess has $fullMatch full matches and $partialMatch partial matches.")
           if (isWon(complete, attempts, maxAttemptsCount)) {
               println("Congratulations! You guessed it!")
               complete = true
           } else if (isLost(complete, attempts, maxAttemptsCount)) {
               println("Sorry, you lost! :( My word is $secret")
               complete = true
           }

       } while (!complete)
}
fun printRoundResults(secret: String, guess: String) {
    println("Your guess has ${countExactMatches(secret, guess)} full matches and ${countPartialMatches(secret, guess)} partial matches.")
}
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int):Boolean {
    if (complete && attempts <= maxAttemptsCount) {
        return true
    }
    return false
}
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int):Boolean = !complete && attempts > maxAttemptsCount

fun main() {
    val wordLength = 4
    val maxAttemptsCount = 3
    val secretExample = "ACEB"
    println(getGameRules(wordLength, maxAttemptsCount, secretExample))
    playGame(generateSecret(), wordLength, maxAttemptsCount)
}
