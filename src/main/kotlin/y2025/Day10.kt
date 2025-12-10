package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import com.microsoft.z3.ArithExpr
import com.microsoft.z3.Context
import com.microsoft.z3.IntSort
import com.microsoft.z3.Status
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

            measureTime {
                check(solve2Z3(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
            }.also { println("Part 2 example 1 finished in $it") }
            measureTime {
                println(solve2Z3(parseInput(input)))
            }.also { println("Part 2 finished in $it") }
        }
    }

object Day10 {
    const val DAY: Int = 10

    data class LightsRow(
        val target: Long,
        val buttons: Collection<Button>,
        val joltageTargets: List<Long>,
    )

    data class Button(
        val bitMap: Long,
        val indexes: Collection<Int>,
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
                        Button(
                            bitMap = values.fold(0L) { acc, v -> acc + (1.shl(v)) },
                            indexes = values,
                        )
                    },
                joltageTargets = joltages.dropLast(1).split(",").map { it.toLong() },
            )
        }

    fun solve1(input: List<LightsRow>): String = input.sumOf(Day10::fewestButtonPressesLights).toString()

    fun fewestButtonPressesLights(row: LightsRow): Long {
        val queue = ArrayDeque(listOf(0L to 0L))
        while (queue.isNotEmpty()) {
            val (lights, pressesSoFar) = queue.removeFirst()
            row.buttons.forEach { button ->
                val newLights = lights xor button.bitMap
                val newPresses = pressesSoFar + 1
                if (newLights == row.target) {
                    return newPresses
                }
                queue.addLast(newLights to newPresses)
            }
        }
        throw IllegalStateException("Should have found something")
    }

    fun solve2(input: List<LightsRow>): String = input.sumOf(Day10::fewestButtonPressesJoltages).toString()

    // My solution, runs out of memory and takes too long
    fun fewestButtonPressesJoltages(row: LightsRow): Long {
        val queue = ArrayDeque(listOf(Triple(row.joltageTargets.map { 0L }, 0, 0L)))
        while (queue.isNotEmpty()) {
            val (joltages, rightmostPressedButton, pressesSoFar) = queue.removeFirst()
            row.buttons.withIndex().forEach { (buttonIndex, button) ->
                if (buttonIndex < rightmostPressedButton) return@forEach
                val newJoltages = joltages.withIndex().map { (i, v) -> if (i in button.indexes) v + 1 else v }
                val newPresses = pressesSoFar + 1
                var hitTarget = true
                for ((i, v) in newJoltages.withIndex()) {
                    val target = row.joltageTargets[i]
                    if (v > target) return@forEach
                    if (v != target) {
                        hitTarget = false
                    }
                }
                if (hitTarget) return newPresses
                queue.addLast(Triple(newJoltages, buttonIndex, newPresses))
            }
        }
        throw IllegalStateException("Should have found something")
    }

    fun solve2Z3(input: List<LightsRow>): String = input.sumOf(Day10::fewestButtonPressesJoltagesZ3).toString()

    // Reddit user sim642's Scala solution
    fun fewestButtonPressesJoltagesZ3(row: LightsRow): Long {
        with(Context(mapOf("model" to "true"))) {
            val s = mkOptimize()

            val buttonPresses = row.buttons.indices.map { mkIntConst("button$it") }
            buttonPresses.forEach { s.Add(mkGe(it, mkInt(0))) }

            val totalPresses = buttonPresses.fold<_, ArithExpr<IntSort>>(mkInt(0)) { a, b -> mkAdd(a, b) }
            s.MkMinimize(totalPresses)

            row.buttons
                .zip(buttonPresses)
                .fold(row.joltageTargets.map<_, ArithExpr<IntSort>> { mkInt(it) }) { acc, buttonPair ->
                    val (button, presses) = buttonPair
                    button.indexes.fold(acc) { acc2, buttonI ->
                        acc2.mapIndexed { accI, num -> if (accI == buttonI) mkSub(num, presses) else num }
                    }
                }.forEach { s.Add(mkEq(it, mkInt(0))) }
            check(s.Check() == Status.SATISFIABLE) { "Expected satisfiable" }
            return s.model
                .evaluate(totalPresses, false)
                .toString()
                .toLong()
        }
    }

    const val EXAMPLE_1 = """[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
"""

    const val ANSWER_1 = "7"

    const val ANSWER_2 = "33"
}
