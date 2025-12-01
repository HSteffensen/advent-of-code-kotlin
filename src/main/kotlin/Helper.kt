package com.hsteffensen

import java.io.File

fun readInput(day: Int) = File("./.local/day${day.toString().padStart(2,'0')}/input.txt").readText(Charsets.UTF_8)
