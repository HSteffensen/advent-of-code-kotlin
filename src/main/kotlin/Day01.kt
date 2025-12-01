package com.hsteffensen

fun main() {
    assert(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
    println(solve1(parseInput(readInput(1))))
}

fun parseInput(input: String) =
    input.trim().lines().filter { it.isNotEmpty() }.map { line ->
        println("line: $line")
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
