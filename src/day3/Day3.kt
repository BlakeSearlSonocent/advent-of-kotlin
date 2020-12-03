package day3

import util.readFileToLines

fun slopDescender(input: List<String>, x: Int, y: Int): Long {
    var pos = 0
    var count: Long = 0
    val width = input.first().length

    for (i in y until input.size step y) {
        val line = input[i]
        pos = (pos + x) % width
        if (line[pos] == '#') count++
    }

    return count
}

fun main() {
    val input = readFileToLines("src/day3/resources/Day3.txt")

    val partOne = slopDescender(input, 3, 1)
    val partTwo = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2).map { (x, y) -> slopDescender(input, x, y) }
        .reduce { acc, it -> acc * it }

    println("Part one: $partOne")
    println("Part two: $partTwo")
}