package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day6) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
//            check(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
//            println(solve2(parseInput(input)))
        }
    }

object Day6 {
    const val DAY: Int = 6

    class Problem(
        val parameters: List<Long>,
        val operation: List<Long>.() -> Long,
    )

    fun parseInput(input: String): List<Problem> {
        val inputGrid =
            input.trim().lines().map { line ->
                line.split(" ").filter { it != "" }
            }
        val transposed = (0..<inputGrid.first().size).map { i -> inputGrid.map { it[i] } }

        return transposed.map { line ->
            Problem(
                parameters = line.dropLast(1).map { it.toLong() },
                operation =
                    when (line.last()) {
                        "+" -> {
                            List<Long>::sum
                        }

                        "*" -> {
                            { reduce { a, b -> a * b } }
                        }

                        else -> {
                            throw IllegalArgumentException("operation can only be + or *")
                        }
                    },
            )
        }
    }

    fun solve1(input: List<Problem>): String = input.sumOf { it.parameters.run(it.operation) }.toString()

    fun solve2(input: List<String>): String {
        TODO()
    }

    const val EXAMPLE_1 = """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  
"""

    const val ANSWER_1 = "4277556"

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
