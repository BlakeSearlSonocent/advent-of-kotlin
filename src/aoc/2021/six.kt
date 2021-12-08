package aoc.`2021`

import util.readFileToText

fun main() {
    var shoal = readFileToText("2021.6.txt").split(",").map { it.toInt() }.groupingBy { it }.eachCount()
        .map { it.key to it.value.toLong() }.toMap()
    println(shoal)

    repeat(256) {
        shoal = shoal.tick()
        println(shoal)
    }

    println(shoal.values.sum())
}

fun Map<Int, Long>.tick(): Map<Int, Long> {
    val newShoal = mutableMapOf<Int, Long>()

    for (i in 0..7) {
        newShoal[i] = this.getOrDefault(i + 1, 0)
    }

    val birthers = this.getOrDefault(0, 0)
    newShoal[8] = birthers

    newShoal[6] = newShoal.getOrDefault(6, 0) + birthers

    return newShoal
}
