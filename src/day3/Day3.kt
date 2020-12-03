package day3

import java.io.File

fun getLinesFromFile(file: String) = File(file).readLines()

fun slopDescender(input: List<String>, x: Int, y: Int): Long {
    var pos = 0
    var count: Long = 0
    val width = input.first().length
    val lines = input.drop(1)

    for (i in (y-1) until lines.size step y) {
        val line = lines[i]
        pos = (pos + x) % width
        if (line[pos] == '#') count++
    }

    return count
}

fun main() {
    val input = getLinesFromFile("src/day3/resources/Day3.txt")
    val first = slopDescender(input, 1, 1)
    val second = slopDescender(input, 3, 1)
    val third = slopDescender(input, 5, 1)
    val fourth = slopDescender(input, 7, 1)
    val fifth = slopDescender(input, 1, 2)
    val result = first * second * third * fourth * fifth
    println("first: $first, second $second, third $third, fourth $fourth, fifth $fifth, result = $result")
}