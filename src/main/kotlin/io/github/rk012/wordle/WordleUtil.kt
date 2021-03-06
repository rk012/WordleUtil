package io.github.rk012.wordle

typealias ResultRow = List<Results>

/**
 * Returns the Wordle result of a given guess with a target word.
 *
 * @param input The guess.
 * @param target The target word.
 *
 * @return The results.
 *
 * @see Results
 */
fun getFilter(input: String, target: String): List<Results> {
    val filter = MutableList(5) { Results.NONE }

    val letterCount = mutableMapOf<Char, Int>()
    target.forEach {
        letterCount[it] = letterCount.getDefault(it) + 1
    }

    target.forEachIndexed { index, c ->
        if (c == input[index]) {
            filter[index] = Results.MATCHES
            letterCount[c] = letterCount[c]!! - 1
        }
    }

    target.forEachIndexed { index, _ ->
        if (filter[index] != Results.MATCHES && letterCount.getDefault(input[index]) > 0) {
            filter[index] = Results.EXISTS
            letterCount[input[index]] = letterCount.getDefault(input[index]) - 1
        }
    }

    return filter
}

internal fun <T> Map<T, Int>.getDefault(key: T): Int = get(key) ?: 0
