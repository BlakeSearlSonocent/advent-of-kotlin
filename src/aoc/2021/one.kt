package aoc.`2021`

import util.readInts

fun main() {
    val depths = readInts("2021.1.txt")
    val pairs = depths.zipWithNext()
    println(pairs.count { (x, y) -> x > y })

    val windows = depths.windowed(3).map { it.sum() }
    val partTwoPairs = windows.zipWithNext()
    println(partTwoPairs.count { (x, y) -> x > y })
}
