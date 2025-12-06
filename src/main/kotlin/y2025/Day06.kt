package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day6) {
            val input = readInput(DAY)
            check(solve1(EXAMPLE_1) == ANSWER_1) { "Example 1 failed" }
            println(solve1(input))
            check(solve2(EXAMPLE_1) == ANSWER_2) { "Example 2 failed" }
            println(solve2(input))
        }
    }

object Day6 {
    const val DAY: Int = 6

    fun solve1(input: String): String {
        val inputGrid =
            input.trim().lines().map { line ->
                line.split(" ").filter { it != "" }
            }
        val transposed = (0..<inputGrid.first().size).map { i -> inputGrid.map { it[i] } }

        return transposed
            .sumOf { line ->
                val parameters = line.dropLast(1).map { it.toLong() }
                when (line.last()) {
                    "+" -> {
                        parameters.sum()
                    }

                    "*" -> {
                        parameters.reduce { a, b -> a * b }
                    }

                    else -> {
                        throw IllegalArgumentException("operation can only be + or *")
                    }
                }
            }.toString()
    }

    fun solve2(input: String): String {
        val lines = input.trim().lines()
        val transposedInput =
            (0..<lines.maxBy { it.length }.length)
                .map { i -> lines.map { it.getOrElse(i) { ' ' } }.joinToString("").trim() }
                .reversed()
                .joinToString("\n")

        return transposedInput
            .split("\n\n")
            .sumOf { chunk ->
                val numbers = chunk.dropLast(1).split("\n").map { it.trim().toLong() }
                when (chunk.last()) {
                    '+' -> numbers.sum()
                    '*' -> numbers.reduce { a, b -> a * b }
                    else -> throw IllegalArgumentException("operation can only be + or *")
                }
            }.toString()
    }

    const val EXAMPLE_1 = """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  
"""

    const val ANSWER_1 = "4277556"

    const val ANSWER_2 = "3263827"
}
