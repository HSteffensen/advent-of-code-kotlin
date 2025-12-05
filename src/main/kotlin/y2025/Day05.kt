package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.math.max

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day5) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
            val example2 = solve2(parseInput(EXAMPLE_1))
            check(example2 == ANSWER_2) { "Example 2 failed. Expected '$ANSWER_2', got '$example2'" }
            println(solve2(parseInput(input)))
        }
    }

object Day5 {
    const val DAY: Int = 5

    data class Input(
        val freshRanges: Collection<LongRange>,
        val ids: Collection<Long>,
    )

    fun parseInput(input: String): Input {
        val (rangeLines, idLines) = input.split("\n\n", limit = 2)

        return Input(
            freshRanges =
                rangeLines.trim().lines().map { line ->
                    val (start, end) = line.split("-", limit = 2)
                    start.toLong()..end.toLong()
                },
            ids = idLines.trim().lines().map { it.toLong() },
        )
    }

    fun solve1(input: Input): String = input.ids.count { id -> input.freshRanges.any { id in it } }.toString()

    fun solve2(input: Input): String {
        val ranges = input.freshRanges.sortedBy { it.last }.sortedBy { it.first }

        val (almostFinalCount, finalRange) =
            ranges.drop(1).fold(0L to ranges.first()) { (count, currentRange), range ->
                if (range.first <= currentRange.last) {
                    count to (currentRange.first..max(range.last, currentRange.last))
                } else {
                    (count + currentRange.last - currentRange.first + 1) to range
                }
            }

        return (almostFinalCount + finalRange.last - finalRange.first + 1).toString()
    }

    const val EXAMPLE_1 = """3-5
10-14
16-20
12-18

1
5
8
11
17
32
"""

    const val ANSWER_1 = "3"

    const val ANSWER_2 = "14"
}
