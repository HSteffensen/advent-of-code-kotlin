package com.hsteffensen.y2025

import com.hsteffensen.readInput

fun main() {
    with(Day01) {
        assert(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
        println(solve1(parseInput(readInput(1))))
        assert(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
        assert(solve2(parseInput(EXAMPLE_3)) == ANSWER_3) { "Example 3 failed" }
        println(solve2(parseInput(readInput(1))))
    }
}

object Day01 {
    fun parseInput(input: String) =
        input.trim().lines().filter { it.isNotEmpty() }.map { line ->
            when (line[0]) {
                'L' -> -1
                'R' -> 1
                else -> throw IllegalArgumentException("inputs are always L or R")
            } * line.drop(1).toInt()
        }

    fun solve1(input: List<Int>): String =
        input
            .fold(50 to 0) { (pos, count), move ->
                val newPos = (pos + move).mod(100)
                newPos to (count + if (newPos == 0)1 else 0)
            }.second
            .toString()

    fun solve2(input: List<Int>): String =
        input
            .fold(50 to 0) { (prevPos, count), move ->
                val posMoved = prevPos + move
                val countInc =
                    if (posMoved <= 0) {
                        (posMoved / -100) + if (prevPos == 0) 0 else 1
                    } else if (posMoved >= 100) {
                        posMoved / 100
                    } else {
                        0
                    }
                val newPos = posMoved.mod(100)
                newPos to count + countInc
            }.second
            .toString()

    const val EXAMPLE_1 = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82
"""

    const val ANSWER_1 = "3"

    const val ANSWER_2 = "6"

    const val EXAMPLE_3 = """L168
L130
R148
L105
R160
L155
L101
L199
R114
L182
"""

    const val ANSWER_3 = "16"
}
