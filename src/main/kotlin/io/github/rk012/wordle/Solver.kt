package io.github.rk012.wordle

import kotlin.math.log2

/**
 * Solver utility for Wordle.
 *
 * @property wordList The list of possible words.
 * @property possibleWords The list of possible words including information from guesses.
 *
 * @constructor Creates a new Solver instance.
 */
class Solver(private val wordList: List<String>) {
    var possibleWords = wordList

    /**
     * Creates a new Solver instance with a specified allowed guess list and possible answer list.
     *
     * @param wordList The list of allowed words.
     * @param answerList The list of possible answers.
     */
    constructor(wordList: List<String>, answerList: List<String>) : this(wordList) {
        this.possibleWords = answerList
    }

    /**
     * Adds a guess and result to narrow the possible words. If resulting wordlist is empty, nothing happens.
     *
     * @param guess The guess to add.
     * @param results The result of the guess.
     *
     * @return The next recommended guess, or null if the list of possible words is empty.
     */
    fun addGuess(guess: String, results: ResultRow): String? {
        val newList = possibleWords.filter(guess, results)

        if (newList.isEmpty()) return null

        possibleWords = newList
        return nextGuess()
    }

    /**
     * Returns the next best guess given previous information, or null if the list of possible words is empty.
     */
    fun nextGuess(): String = if (possibleWords.size > 1) nextGuesses().maxByOrNull { it.value }!!.key else possibleWords[0]

    /**
     * Generates a map of possible guesses and their respective entropy.
     */
    fun nextGuesses(): Map<String, Double> = wordList.associateWith { entropy(it, possibleWords) }

    companion object {
        /**
         * Calculates the expected entropy of a word across a given list of words.
         *
         * @param word The word to calculate the entropy of.
         * @param wordList The list of words.
         *
         * @return The expected entropy of the word.
         */
        fun entropy(word: String, wordList: List<String>): Double {
            val results = mutableMapOf<ResultRow, Int>()

            wordList.forEach {
                val result = getFilter(word, it)
                results[result] = results.getDefault(result) + 1
            }

            return results.map {
                it.value.toDouble() / wordList.size * -log2(it.value.toDouble() / wordList.size)
            }.sum()
        }

        /**
         * Filters a wordlist based on a given word and its result.
         *
         * @param word The word to filter the wordlist by.
         * @param results The results of the word.
         *
         * @return The filtered wordlist.
         *
         * @receiver The wordlist to filter.
         */
        fun List<String>.filter(word: String, results: ResultRow): List<String> = filter { getFilter(word, it) == results }
    }
}
