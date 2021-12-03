package util

import java.io.File

fun readLines(path: String) = File("resources/$path").readLines()

fun readFileToInts(path: String) = readLines(path).map { it.toInt() }

fun readFileToLongs(path: String) = readLines(path).map { it.toLong() }.toMutableList()

fun readDoubleLineSeparatedFileToGroups(path: String) = File("resources/$path").readText().split("\n\n")

fun readFileToText(path: String) = File("resources/$path").readText()
