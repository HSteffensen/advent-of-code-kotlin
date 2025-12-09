package com.hsteffensen.y2025

import com.hsteffensen.Grid
import com.hsteffensen.Pos2d
import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day9) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
            check(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
            println(solve2(parseInput(input)))
        }
    }

object Day9 {
    const val DAY: Int = 9

    fun parseInput(input: String): List<Pos2d> =
        input.trim().lines().map { line ->
            val (x, y) = line.split(",")
            x.toLong() to y.toLong()
        }

    fun solve1(input: List<Pos2d>): String {
        var largestFound = 0L
        for ((i, a) in input.withIndex()) {
            for (b in input.asSequence().drop(i + 1)) {
                val dx = (a.first - b.first).absoluteValue + 1
                val dy = (a.second - b.second).absoluteValue + 1
                val area = dx * dy
                largestFound = max(largestFound, area)
            }
        }
        return largestFound.toString()
    }

    enum class TileColor {
        RedCorner,
        GreenBorder,
    }

    fun solve2(input: List<Pos2d>): String {
        val tiles =
            Grid
                .build {
                    var currentCorner = input.last()
                    for (nextCorner in input) {
                        set(nextCorner, TileColor.RedCorner)
                        if (currentCorner.first == nextCorner.first) {
                            for (y in (min(currentCorner.second, nextCorner.second) + 1)..<max(currentCorner.second, nextCorner.second)) {
                                set(currentCorner.first, y, TileColor.GreenBorder)
                            }
                        } else if (currentCorner.second == nextCorner.second) {
                            for (x in (min(currentCorner.first, nextCorner.first) + 1)..<max(currentCorner.first, nextCorner.first)) {
                                set(x, currentCorner.second, TileColor.GreenBorder)
                            }
                        } else {
                            throw IllegalArgumentException("Connected lines are expected to share an axis")
                        }
                        currentCorner = nextCorner
                    }
                }

        var largestFound = 0L
        for ((i, a) in input.withIndex()) {
            for (b in input.asSequence().drop(i + 1)) {
                val dx = (a.first - b.first).absoluteValue + 1
                val dy = (a.second - b.second).absoluteValue + 1
                val area = dx * dy
                if (area > largestFound && tiles.allInside(a, b)) {
                    largestFound = area
                }
            }
        }
        return largestFound.toString()
    }

    fun Grid<TileColor>.allInside(
        a: Pos2d,
        b: Pos2d,
    ): Boolean {
        val x1 = min(a.first, b.first)
        val x2 = max(a.first, b.first)
        val y1 = min(a.second, b.second)
        val y2 = max(a.second, b.second)
        for ((borderX, borderY) in grid.keys) {
            if (borderX in (x1 + 1)..<x2 && borderY in (y1 + 1)..<y2) return false
        }
        return true
    }

    const val EXAMPLE_1 = """7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3
"""

    const val ANSWER_1 = "50"

    const val ANSWER_2 = "24"
}
