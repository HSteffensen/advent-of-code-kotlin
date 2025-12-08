package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import java.util.*
import kotlin.math.sign
import kotlin.math.sqrt

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day8) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1), 10) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
            check(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
            println(solve2(parseInput(input)))
        }
    }

object Day8 {
    const val DAY: Int = 8

    data class Pos3d(
        val x: Long,
        val y: Long,
        val z: Long,
    )

    fun parseInput(input: String): List<Pos3d> =
        input.trim().lines().map { line ->
            val (x, y, z) = line.split(",")
            Pos3d(x.toLong(), y.toLong(), z.toLong())
        }

    fun distance(
        a: Pos3d,
        b: Pos3d,
    ): Double {
        val dx = b.x - a.x
        val dy = b.y - a.y
        val dz = b.z - a.z
        return sqrt((dx * dx + dy * dy + dz * dz).toDouble())
    }

    fun closestBoxes(
        input: List<Pos3d>,
        amount: Int,
    ): Collection<Pair<Pos3d, Pos3d>> {
        val closestConnections = sortedSetOf<Triple<Pos3d, Pos3d, Double>>({ a, b -> (a.third - b.third).sign.toInt() })
        for ((i, posA) in input.withIndex()) {
            for (posB in input.drop(i + 1)) {
                val distance = distance(posA, posB)
                val connection = Triple(posA, posB, distance)
                if (closestConnections.size < amount) {
                    closestConnections.add(connection)
                } else if (distance < closestConnections.last().third) {
                    closestConnections.removeLast()
                    closestConnections.add(connection)
                }
            }
        }
        return closestConnections.map { (a, b, _) -> a to b }
    }

    fun Map<Pos3d, Pos3d>.rootConnection(pos: Pos3d): Pos3d {
        var currentPos = pos
        var nextPos = getOrDefault(currentPos, currentPos)
        while (nextPos != currentPos) {
            currentPos = nextPos
            nextPos = getOrDefault(currentPos, currentPos)
        }
        return nextPos
    }

    fun solve1(
        input: List<Pos3d>,
        amount: Int = 1000,
    ): String {
        val closestConnections = closestBoxes(input, amount)
        val groups = input.associateWith { it }.toMutableMap()
        for ((posA, posB) in closestConnections) {
            val rootA = groups.rootConnection(posA)
            val rootB = groups.rootConnection(posB)
            groups[rootB] = rootA
            groups[posA] = rootA
            groups[posB] = rootA
        }
        return groups.keys
            .groupBy { groups.rootConnection(it) }
            .values
            .map { it.size }
            .sortedDescending()
            .take(3)
            .reduce { a, b -> a * b }
            .toString()
    }

    fun closestBoxes(input: List<Pos3d>): SortedSet<Triple<Pos3d, Pos3d, Double>> {
        val closestConnections = sortedSetOf<Triple<Pos3d, Pos3d, Double>>({ a, b -> (a.third - b.third).sign.toInt() })
        for ((i, posA) in input.withIndex()) {
            for (posB in input.drop(i + 1)) {
                val distance = distance(posA, posB)
                closestConnections.add(Triple(posA, posB, distance))
            }
        }
        return closestConnections
    }

    fun solve2(input: List<Pos3d>): String {
        val closestConnections = closestBoxes(input)
        val groups = input.associateWith { it }.toMutableMap()
        val groupIds = input.toMutableSet()
        for ((posA, posB) in closestConnections.sortedBy { it.third }) {
            val rootA = groups.rootConnection(posA)
            val rootB = groups.rootConnection(posB)
            groups[rootB] = rootA
            groups[posA] = rootA
            groups[posB] = rootA
            groupIds.remove(posA)
            groupIds.remove(posB)
            groupIds.remove(rootB)
            groupIds.add(rootA)
            if (groupIds.size == 1) {
                return (posA.x * posB.x).toString()
            }
        }
        return "failure"
    }

    const val EXAMPLE_1 = """162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689
"""

    const val ANSWER_1 = "40"

    const val ANSWER_2 = "25272"
}
