package util

import java.io.File

fun readFileToLines(path: String) = File("resources/$path").readLines()

fun readFileToInts(path: String) = readFileToLines(path).map { it.toInt() }

fun readFileToLongs(path: String) = readFileToLines(path).map { it.toLong() }.toMutableList()

fun readDoubleLineSeparatedFileToGroups(path: String) = File("resources/$path").readText().split("\n\n")

fun readFileToText(path: String) = File("resources/$path").readText()
