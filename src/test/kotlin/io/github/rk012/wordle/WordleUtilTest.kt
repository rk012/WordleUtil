package io.github.rk012.wordle

import kotlin.test.Test
import kotlin.test.assertEquals

class WordleUtilTest {
    @Test
    fun filterTest() {
        assertEquals(
            listOf(Results.MATCHES, Results.NONE, Results.EXISTS, Results.NONE, Results.NONE),
            getFilter("think", "trait")
        )

        assertEquals(
            listOf(Results.EXISTS, Results.NONE, Results.EXISTS, Results.NONE, Results.NONE),
            getFilter("oboes", "kazoo")
        )
    }
}
