package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.time.measureTime

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day12) {
            val input = readInput(DAY)

//            measureTime {
//                check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
//            }.also { println("Part 1 example 1 finished in $it") }
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

object Day12 {
    const val DAY: Int = 12

    data class Input(
        val presents: List<Present>,
        val trees: List<Tree>,
    )

    data class Present(
        val grid: List<List<Boolean>>,
        val size: Int,
    )

    data class Tree(
        val width: Int,
        val height: Int,
        val presents: List<Int>,
    )

    fun parseInput(input: String): Input {
        val sections = input.trim().split("\n\n")
        val presents =
            sections.dropLast(1).map { presentText ->
                val grid =
                    presentText.trim().lines().drop(1).map { line ->
                        line.map { c ->
                            when (c) {
                                '.' -> false
                                '#' -> true
                                else -> throw IllegalArgumentException("Presents should only have . and #")
                            }
                        }
                    }
                Present(grid = grid, size = grid.flatten().count { it })
            }
        val trees =
            sections
                .last()
                .trim()
                .lines()
                .map { line ->
                    val (size, amountsText) = line.split(":")
                    val (widthText, heightText) = size.split("x")
                    val amounts = amountsText.trim().split(" ").map { it.toInt() }
                    Tree(width = widthText.toInt(), height = heightText.toInt(), presents = amounts)
                }
        return Input(presents = presents, trees = trees)
    }

    fun solve1(input: Input): String =
        input.trees
            .count { tree ->
                val size = tree.width * tree.height
                val presentsSize = tree.presents.zip(input.presents).sumOf { (count, p) -> p.size * count }
                size >= presentsSize
            }.toString()

    fun solve2(input: List<String>): String {
        TODO()
    }

    const val EXAMPLE_1 = """0:
###
##.
##.

1:
###
##.
.##

2:
.##
###
##.

3:
##.
###
##.

4:
###
#..
###

5:
###
.#.
###

4x4: 0 0 0 0 2 0
12x5: 1 0 1 0 2 2
12x5: 1 0 1 0 3 2
"""

    const val ANSWER_1 = "2"

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
