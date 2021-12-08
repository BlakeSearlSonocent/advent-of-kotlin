package aoc.`2021`

import util.readFileToText
import kotlin.math.abs

fun main() {
    val positions = readFileToText("2021.7.txt").split(",").map { it.toInt() }
    val distances = mutableListOf<Int>()
    for (position in positions) {
        distances += positions.sumOf { abs(it - position) }
    }

    println(distances.minOf { it })

    val fuelCosts = mutableListOf<Int>()
    val min = positions.minOf { it }
    val max = positions.maxOf { it }
    for (position in min..max) {
        fuelCosts += positions.sumOf { fuelCost(it, position) }
    }

    println(fuelCosts.minOf { it })
}

fun fuelCost(start: Int, finish: Int): Int {
    var fuelCost = 0
    if (finish == start) {
        return fuelCost
    }

    return if (finish > start) {
        fuelCost + finish - start + fuelCost(start + 1, finish)
    } else {
        fuelCost + start - finish + fuelCost(start, finish + 1)
    }
}
