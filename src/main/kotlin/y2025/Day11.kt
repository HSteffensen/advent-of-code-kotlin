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

            measureTime {
                check(solve2(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
            }.also { println("Part 2 example 1 finished in $it") }
            measureTime {
                println(solve2(parseInput(input)))
            }.also { println("Part 2 finished in $it") }

            println("Alternate solution")
            measureTime {
                check(solve1Alt(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            }.also { println("Part 1 example 1 finished in $it") }
            measureTime {
                println(solve1Alt(parseInput(input)))
            }.also { println("Part 1 finished in $it") }
            measureTime {
                check(solve2Alt(parseInput(EXAMPLE_2)) == ANSWER_2) { "Example 2 failed" }
            }.also { println("Part 2 example 1 finished in $it") }
            measureTime {
                println(solve2Alt(parseInput(input)))
            }.also { println("Part 2 finished in $it") }
        }
    }

object Day11 {
    const val DAY: Int = 11

    const val START_1 = "you"
    const val END = "out"
    const val START_2 = "svr"
    const val REQUIRED_DAC = "dac"
    const val REQUIRED_FFT = "fft"

    fun parseInput(input: String): Map<String, List<String>> =
        input.trim().lines().associate { line ->
            val (source, destinationsString) = line.split(":")
            val destinations = destinationsString.trim().split(" ")
            source to destinations
        }

    fun solve1(input: Map<String, List<String>>): String = countPaths1(input, START_1, mutableMapOf()).toString()

    fun countPaths1(
        graph: Map<String, List<String>>,
        current: String,
        cache: MutableMap<String, Long>,
    ): Long {
        if (current == END) return 1
        val cached = cache[current]
        if (cached != null) return cached
        val result =
            graph[current]!!.sumOf {
                countPaths1(graph, it, cache)
            }
        cache[current] = result
        return result
    }

    fun solve2(input: Map<String, List<String>>): String =
        countPaths2(
            graph = input,
            current = START_2,
            cache = mutableMapOf(),
            visitedDac = false,
            visitedFft = false,
        ).toString()

    fun countPaths2(
        graph: Map<String, List<String>>,
        current: String,
        cache: MutableMap<Triple<String, Boolean, Boolean>, Long>,
        visitedDac: Boolean,
        visitedFft: Boolean,
    ): Long {
        if (current == END) return if (visitedDac && visitedFft) 1 else 0
        val newVisitedDac = if (current == REQUIRED_DAC) true else visitedDac
        val newVisitedFft = if (current == REQUIRED_FFT) true else visitedFft
        val cacheItem = Triple(current, newVisitedDac, newVisitedFft)
        val cached = cache[cacheItem]
        if (cached != null) return cached
        val result =
            graph[current]!!.sumOf {
                countPaths2(graph, it, cache, newVisitedDac, newVisitedFft)
            }
        cache[cacheItem] = result
        return result
    }

    fun solve1Alt(input: Map<String, List<String>>): String = countPathsBetween(input, START_1, END).toString()

    fun solve2Alt(input: Map<String, List<String>>): String =
        (
            countPathsBetween(input, START_2, REQUIRED_FFT) * countPathsBetween(input, REQUIRED_FFT, REQUIRED_DAC) *
                countPathsBetween(input, REQUIRED_DAC, END) +
                countPathsBetween(input, START_2, REQUIRED_DAC) * countPathsBetween(input, REQUIRED_DAC, REQUIRED_FFT) *
                countPathsBetween(input, REQUIRED_FFT, END)
        ).toString()

    fun countPathsBetween(
        graph: Map<String, List<String>>,
        current: String,
        target: String,
        cache: MutableMap<String, Long> = mutableMapOf(),
    ): Long {
        if (current == target) return 1
        if (current == END) return 0
        val cached = cache[current]
        if (cached != null) return cached
        val result =
            graph[current]!!.sumOf {
                countPathsBetween(graph, it, target, cache)
            }
        cache[current] = result
        return result
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

    const val EXAMPLE_2 = """svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out
"""

    const val ANSWER_2 = "2"
}
