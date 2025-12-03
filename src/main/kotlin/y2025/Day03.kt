package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.math.max

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day3) {
            val input = readInput(DAY)
            assert(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
//            assert(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
//            println(solve2(parseInput(input)))
        }
    }

object Day3 {
    const val DAY: Int = 3

    fun parseInput(input: String): List<List<Int>> = input.trim().lines().map { line -> line.toCharArray().map { it.digitToInt() } }

    fun solve1(input: List<List<Int>>): String = input.sumOf { maxJoltageForBank(it) }.toString()

    fun maxJoltageForBank(batteries: List<Int>): Int {
        var max = 0
        for ((i, v) in batteries.dropLast(1).withIndex()) {
            val joltage = (10 * v) + batteries.subList(i + 1, batteries.size).max()
            max = max(max, joltage)
        }
        return max
    }

    fun solve2(input: List<List<Int>>): String {
        TODO()
    }

    const val EXAMPLE_1 = """987654321111111
811111111111119
234234234234278
818181911112111
"""

    const val ANSWER_1 = "357"

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
