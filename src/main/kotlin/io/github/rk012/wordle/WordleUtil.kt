package io.github.rk012.wordle

/**
 * Returns the Wordle result of a given guess with a target word.
 *
 * @param input the guess.
 * @param target the target word.
 *
 * @return the results.
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

/**
 * Returns the value associated with the specified key, or 0 if not present.
 *
 * @param T the type of key.
 * @param key the key whose associated value is to be returned.
 *
 * @return the value associated with the specified key, or 0 if not present.
 */
private fun<T> Map<T, Int>.getDefault(key: T): Int = get(key) ?: 0
