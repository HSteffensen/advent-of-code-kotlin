package com.hsteffensen

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(DayX) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
//            check(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
//            println(solve2(parseInput(input)))
        }
    }

object DayX {
    val DAY: Int = TODO()

    fun parseInput(input: String): List<String> = TODO()

    fun solve1(input: List<String>): String {
        TODO()
    }

    fun solve2(input: List<String>): String {
        TODO()
    }

    const val EXAMPLE_1 = """"""

    const val ANSWER_1 = ""

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
