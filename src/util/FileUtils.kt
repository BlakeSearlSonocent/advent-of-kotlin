package util

import java.io.File

fun readFileToLines(path: String) = File(path).readLines()