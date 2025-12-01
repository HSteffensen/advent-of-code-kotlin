package com.hsteffensen

import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    assert(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
    println(solve1(parseInput(readInput(1))))
    assert(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
    assert(solve2(parseInput(EXAMPLE_3)) == ANSWER_3) { "Example 3 failed" }
    println(solve2(parseInput(readInput(1))))
}

fun parseInput(input: String) =
    input.trim().lines().filter { it.isNotEmpty() }.map { line ->
        when (line[0]) {
            'L' -> -1
            'R' -> 1
            else -> throw IllegalArgumentException("inputs are always L or R")
        } * line.drop(1).toInt()
    }

fun solve1(input: List<Int>): String {
    var pos = 50
    var result = 0
    for (move in input) {
        pos += move
        pos %= 100
        if (pos == 0) {
            result += 1
        }
    }
    return result.toString()
}

fun solve2(input: List<Int>): String {
    var pos = 50
    var result = 0
    for (moveVal in input) {
        var move = moveVal
        while (move.absoluteValue >= 100) {
            result += 1
            move -= move.sign * 100
        }
        if (move == 0) continue
        val startPos = pos
        pos += move
        if (pos <= 0 && startPos != 0) {
            result += 1
        } else if (pos >= 100) {
            result += 1
        }
        pos = pos.mod(100)
    }
    return result.toString()
}

private const val EXAMPLE_1 = """L68
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

private const val ANSWER_1 = "3"

private const val ANSWER_2 = "6"

private const val EXAMPLE_3 = """L168
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

private const val ANSWER_3 = "16"
