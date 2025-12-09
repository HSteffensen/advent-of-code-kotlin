package com.hsteffensen

import kotlin.time.measureTime

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(DayX) {
            val input = readInput(DAY)

            measureTime {
                check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            }.also { println("Part 1 example 1 finished in $it") }
            measureTime {
                println(solve1(parseInput(input)))
            }.also { println("Part 1 finished in $it") }

            measureTime {
                check(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
            }.also { println("Part 2 example 1 finished in $it") }
            measureTime {
                println(solve2(parseInput(input)))
            }.also { println("Part 2 finished in $it") }
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
