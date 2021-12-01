package dayOne

import util.readFileToInts

fun main() {
    val vals = readFileToInts("2021.1.txt")
    val pairs = vals.zipWithNext()
    println(pairs.count { it.second - it.first > 0 })

    val windows = vals.windowed(3).map { it.sum() }
    val partTwoPairs = windows.zipWithNext()
    println(partTwoPairs.count { it.second - it.first > 0 })
}
