package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems
import kotlin.time.measureTime

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day11) {
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

object Day11 {
    const val DAY: Int = 11

    const val START = "you"
    const val END = "out"

    fun parseInput(input: String): Map<String, List<String>> =
        input.trim().lines().associate { line ->
            val (source, destinationsString) = line.split(":")
            val destinations = destinationsString.trim().split(" ")
            source to destinations
        }

    fun solve1(input: Map<String, List<String>>): String = countPaths(input, START, mutableMapOf(END to 1L)).toString()

    fun countPaths(
        graph: Map<String, List<String>>,
        current: String,
        cache: MutableMap<String, Long>,
    ): Long {
        if (current == END) return 1
        val cached = cache[current]
        if (cached != null) return cached
        val result =
            graph[current]!!.sumOf {
                countPaths(graph, it, cache)
            }
        cache[current] = result
        return result
    }

    fun solve2(input: List<String>): String {
        TODO()
    }

    const val EXAMPLE_1 = """aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out
"""

    const val ANSWER_1 = "5"

    const val EXAMPLE_2 = """"""

    const val ANSWER_2 = ""
}
