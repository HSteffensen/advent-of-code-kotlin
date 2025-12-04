package com.hsteffensen.y2025

import com.hsteffensen.Grid
import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day4) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
            check(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
            println(solve2(parseInput(input)))
        }
    }

object Day4 {
    const val DAY: Int = 4

    fun parseInput(input: String): Grid<ForkliftItem> =
        Grid.build {
            input.trim().lines().withIndex().forEach { (y, line) ->
                line.withIndex().forEach { (x, c) ->
                    when (c) {
                        '.' -> set(x, y, ForkliftItem.EMPTY)
                        '@' -> set(x, y, ForkliftItem.PAPER)
                        else -> throw IllegalArgumentException("Unexpected grid item: $c at ($x,$y) in:\n$input")
                    }
                }
            }
        }

    fun solve1(input: Grid<ForkliftItem>): String =
        input.grid.keys
            .count { pos ->
                input.grid[pos] == ForkliftItem.PAPER &&
                    input.neighbors8(pos).count { neighbor -> input.grid[neighbor] == ForkliftItem.PAPER } < 4
            }.toString()

    fun solve2(input: Grid<ForkliftItem>): String = countRemovablePaper(input).toString()

    fun countRemovablePaper(input: Grid<ForkliftItem>): Int {
        val withPaperRemoved = removePaper(input)
        val removedCount =
            input.grid.keys.count { pos ->
                input.grid[pos] == ForkliftItem.PAPER &&
                    withPaperRemoved.grid[pos] != ForkliftItem.PAPER
            }
        return if (removedCount == 0) 0 else removedCount + countRemovablePaper(withPaperRemoved)
    }

    fun removePaper(input: Grid<ForkliftItem>) =
        Grid.build {
            input.grid.keys.forEach { pos ->
                if (input.grid[pos] == ForkliftItem.PAPER &&
                    input.neighbors8(pos).count { neighbor -> input.grid[neighbor] == ForkliftItem.PAPER } >= 4
                ) {
                    set(pos, ForkliftItem.PAPER)
                }
            }
        }

    enum class ForkliftItem {
        EMPTY,
        PAPER,
    }

    const val EXAMPLE_1 = """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.
"""

    const val ANSWER_1 = "13"

    const val ANSWER_2 = "43"
}
