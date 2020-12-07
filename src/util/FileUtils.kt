package util

import java.io.File

fun readFileToLines(path: String) = File(path).readLines()

fun readFileToInts(path: String) = readFileToLines(path).map { it.toInt() }

fun readDoubleLineSeparatedFileToGroups(path: String) = File(path).readText().split("\n\n")

fun readFileToText(path: String) =File(path).readText()