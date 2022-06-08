package io.github.rk012.wordle

/**
 * Lists of valid Wordle words.
 *
 * @property validList The list of valid words.
 * @property solutionList The list of valid solutions.
 */
object Wordlists {
    val validList by lazy { getResource("/wordlists/valid_list.txt")!!.readText().split("\n") }
    val solutionList by lazy { getResource("/wordlists/solution_list.txt")!!.readText().split("\n") }

    private fun getResource(path: String) = this::class.java.getResource(path)
}
