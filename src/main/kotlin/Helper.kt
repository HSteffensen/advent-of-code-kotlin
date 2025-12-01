package com.hsteffensen

import java.io.File
import kotlin.system.exitProcess

fun readInput(day: Int): String =
    try {
        File("./.local/day${day.toString().padStart(2, '0')}/input.txt").readText(Charsets.UTF_8)
    } catch (_: Exception) {
        println("Missing input file for day $day")
        // Gradle is behaving badly and not showing stdout or the exception message/stacktrace, so instead of throwing an error we will swallow it and use print with `exit 0`
        exitProcess(0)
    }
