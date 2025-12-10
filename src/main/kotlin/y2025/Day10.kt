package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.time.measureTime

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day10) {
            val input = readInput(DAY)

            measureTime {
                check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            }.also { println("Part 1 example 1 finished in $it") }
            measureTime {
                println(solve1(parseInput(input)))
            }.also { println("Part 1 finished in $it") }

//            measureTime {
//                check(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
//            }.also { println("Part 2 example 1 finished in $it") }
//            measureTime {
//                println(solve2(parseInput(input)))
//            }.also { println("Part 2 finished in $it") }
        }
    }

object Day10 {
    const val DAY: Int = 10

    data class LightsRow(
        val target: Long,
        val buttons: Collection<Long>,
        val joltageTargets: List<Long>,
    )

    fun parseInput(input: String): List<LightsRow> =
        input.trim().lines().map { line ->
            val (lights, rest) = line.split("]")
            val (buttons, joltages) = rest.split("{")

            LightsRow(
                target =
                    lights
                        .drop(1)
                        .reversed()
                        .replace('.', '0')
                        .replace('#', '1')
                        .toLong(2),
                buttons =
                    buttons.trim().split(" ").map { button ->
                        val values =
                            button
                                .drop(1)
                                .dropLast(1)
                                .split(",")
                                .map { it.toInt() }
                        values.fold(0L) { acc, v -> acc + (1.shl(v)) }
                    },
                joltageTargets = joltages.dropLast(1).split(",").map { it.toLong() },
            )
        }

    fun solve1(input: List<LightsRow>): String = input.sumOf(Day10::fewestButtonPresses).toString()

    fun fewestButtonPresses(row: LightsRow): Long {
        val queue = ArrayDeque(listOf(0L to 0L))
        while (queue.isNotEmpty()) {
            val (lights, pressesSoFar) = queue.removeFirst()
            row.buttons.forEach { button ->
                val newLights = lights xor button
                val newPresses = pressesSoFar + 1
                if (newLights == row.target) {
                    return newPresses
                }
                queue.addLast(newLights to newPresses)
            }
        }
        throw IllegalStateException("Should have found something")
    }

    fun solve2(input: List<LightsRow>): String {
        TODO()
    }

    const val EXAMPLE_1 = """[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
"""

    const val ANSWER_1 = "7"

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
