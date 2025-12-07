package com.hsteffensen.y2025

import com.hsteffensen.Grid
import com.hsteffensen.Pos2d
import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(DayX) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
//            check(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
//            println(solve2(parseInput(input)))
        }
    }

object DayX {
    const val DAY: Int = 7

    enum class TachyonExperimentItem {
        Start,
        Empty,
        Splitter,
        Beam,
    }

    fun parseInput(input: String): Grid<TachyonExperimentItem> =
        Grid.build {
            input.trim().lines().withIndex().forEach { (y, line) ->
                line.withIndex().forEach { (x, c) ->
                    set(
                        x,
                        y,
                        when (c) {
                            '.' -> TachyonExperimentItem.Empty
                            '^' -> TachyonExperimentItem.Splitter
                            'S' -> TachyonExperimentItem.Start
                            else -> throw IllegalArgumentException("Input must only be one of .^S")
                        },
                    )
                }
            }
        }

    fun solve1(input: Grid<TachyonExperimentItem>): String {
        val start =
            input.grid
                .filterValues { it == TachyonExperimentItem.Start }
                .keys
                .singleOrNull()
                ?: throw IllegalStateException("Input is supposed to have only one S")
        val beamGrid = input.grid.toMutableMap()
        propegateBeam(beamGrid, start)
        return beamGrid
            .count { (pos, item) ->
                item == TachyonExperimentItem.Splitter &&
                    beamGrid[pos.first to pos.second - 1] == TachyonExperimentItem.Beam
            }.toString()
    }

    fun solve2(input: List<String>): String {
        TODO()
    }

    // propegate from pos
    fun propegateBeam(
        grid: MutableMap<Pos2d, TachyonExperimentItem>,
        pos: Pos2d,
    ) {
        when (grid[pos]) {
            null, TachyonExperimentItem.Beam -> {
                // outside of the grid or in an already propegated beam area
                return
            }

            TachyonExperimentItem.Start,
            TachyonExperimentItem.Empty,
            -> {
                grid[pos] = TachyonExperimentItem.Beam
                propegateBeam(grid, pos.first to pos.second + 1)
            }

            TachyonExperimentItem.Splitter -> {
                propegateBeam(grid, pos.first - 1 to pos.second)
                propegateBeam(grid, pos.first + 1 to pos.second)
            }
        }
    }

    const val EXAMPLE_1 = """.......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
...............
"""

    const val ANSWER_1 = "21"

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
