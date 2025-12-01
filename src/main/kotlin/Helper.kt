package com.hsteffensen

import java.io.File
import kotlin.system.exitProcess

fun readInput(day: Int): String =
    runAvoidingWeirdGradleProblems {
        File("./.local/day${day.toString().padStart(2, '0')}/input.txt").readText(Charsets.UTF_8)
    }

fun <T> runAvoidingWeirdGradleProblems(f: () -> T) =
    try {
        f()
    } catch (e: Exception) {
        e.printStackTrace()
        exitProcess(0)
    }
