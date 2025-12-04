package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.math.max

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day3) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
            check(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
            println(solve2(parseInput(input)))
        }
    }

object Day3 {
    const val DAY: Int = 3

    fun parseInput(input: String): List<List<Int>> = input.trim().lines().map { line -> line.toCharArray().map { it.digitToInt() } }

    fun solve1(input: List<List<Int>>): String = input.sumOf { maxJoltageForBankPart1(it) }.toString()

    fun maxJoltageForBankPart1(batteries: List<Int>): Int =
        batteries.dropLast(1).withIndex().fold(0) { maxSoFar, (i, v) ->
            max(maxSoFar, (10 * v) + batteries.subList(i + 1, batteries.size).max())
        }

    fun solve2(input: List<List<Int>>): String = input.sumOf { maxJoltageForBankPart2(it) }.toString()

    fun maxJoltageForBankPart2(batteries: List<Int>): Long =
        batteries.fold(mapOf(0 to 0L)) { largestPerSize, v ->
            val newLargestSize = largestPerSize.keys.max() + 1
            val largestPerSizeWithNew = if (newLargestSize <= 12) largestPerSize + mapOf(newLargestSize to 0) else largestPerSize
            largestPerSizeWithNew.mapValues { (key, existingValue) ->
                if (key == 0) return@mapValues 0
                val appendDigit = (largestPerSize[key - 1]!! * 10) + v
                val replaceLastDigit = ((existingValue / 10) * 10) + v
                max(existingValue, max(appendDigit, replaceLastDigit))
            }
        }[12]!!

    const val EXAMPLE_1 = """987654321111111
811111111111119
234234234234278
818181911112111
"""

    const val ANSWER_1 = "357"

    const val ANSWER_2 = "3121910778619"
}
