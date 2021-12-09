package aoc.`2021`

import util.readFileToText
import kotlin.math.abs

fun main() {
    val positions = readFileToText("2021.7.txt").split(",").map { it.toInt() }
    println(positions.map { position -> positions.sumOf { abs(it - position) } }.minOf { it })

    val min = positions.minOf { it }
    val max = positions.maxOf { it }
    println((min..max).map { positions.sumOf { position -> fuelCost(it, position) } }.minOf { it })
}

fun fuelCost(start: Int, finish: Int) = (1..abs(finish - start)).sumOf { it }
