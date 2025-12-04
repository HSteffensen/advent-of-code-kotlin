package com.hsteffensen.y2025

import com.hsteffensen.readInput
import com.hsteffensen.runAvoidingWeirdGradleProblems

fun main(): Unit =
    runAvoidingWeirdGradleProblems {
        with(Day2) {
            val input = readInput(DAY)
            check(solve1(parseInput(EXAMPLE_1)) == ANSWER_1) { "Example 1 failed" }
            println(solve1(parseInput(input)))
            check(solve2(parseInput(EXAMPLE_1)) == ANSWER_2) { "Example 2 failed" }
            println(solve2(parseInput(input)))
        }
    }

object Day2 {
    const val DAY = 2

    fun parseInput(input: String): List<Pair<Long, Long>> =
        input.split(",").map {
            val (a, b) = it.split("-")
            a.toLong() to b.toLong()
        }

    fun solve1(input: List<Pair<Long, Long>>): String =
        input
            .flatMap { (a, b) -> a..b }
            .filter {
                val stringified = it.toString()
                if (stringified.length % 2 != 0) {
                    false
                } else {
                    val front = stringified.take(stringified.length / 2)
                    val back = stringified.takeLast(stringified.length / 2)
                    front == back
                }
            }.sum()
            .toString()

    fun solve2(input: List<Pair<Long, Long>>): String =
        input
            .flatMap { (a, b) -> a..b }
            .filter {
                val stringified = it.toString()
                (1..stringified.length / 2).any { chunkSize ->
                    if (stringified.length % chunkSize != 0) return@any false
                    val chunkCount = stringified.length / chunkSize
                    val front = stringified.take(chunkSize)
                    val repeated = front.repeat(chunkCount)
                    repeated == stringified
                }
            }.sum()
            .toString()

    const val EXAMPLE_1 =
        """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""

    const val ANSWER_1 = "1227775554"

    const val ANSWER_2 = "4174379265"
}
