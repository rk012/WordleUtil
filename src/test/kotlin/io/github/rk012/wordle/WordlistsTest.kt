package io.github.rk012.wordle

import kotlin.test.Test
import kotlin.test.assertContains

class WordlistsTest {
    @Test
    fun wordListTest() {
        val valid = Wordlists.validList
        val solution = Wordlists.solutionList

        assertContains(valid, "aback")
        assertContains(solution, "aback")

        assertContains(valid, "zeros")
        assert("zeros" !in solution)
    }
}