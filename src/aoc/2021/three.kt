package aoc.`2021`

import util.readLines
import util.toDecimal

fun main() {
    val binaries = readLines("2021.3.txt").map { line -> line.map { digit -> digit.digitToInt() } }

    val gamma = binaries.mostCommon()
    val epsilon = gamma.map { 1 - it }

    println("Part one: ${toDecimal(epsilon.joinDigits()) * toDecimal(gamma.joinDigits())}")

    val oxygen = binaries.calculateRating { x, y -> x == y }
    val dioxide = binaries.calculateRating { x, y -> x != y }

    println("Part two: ${toDecimal(oxygen.first().joinDigits()) * toDecimal(dioxide.first().joinDigits())}")
}

private fun List<List<Int>>.mostCommon() =
    reduce { acc, next -> next.mapIndexed { i, bit -> bit + acc[i] } }.map { if (2 * it >= size) 1 else 0 }

private fun List<Int>.joinDigits() = joinToString("").toLong()

private fun List<List<Int>>.calculateRating(fn: (Int, Int) -> Boolean): MutableList<List<Int>> {
    var rating = this.toMutableList()
    var index = 0
    while (rating.size > 1) {
        val mostCommon = rating.mostCommon()[index]
        rating.removeIf { fn(it[index], mostCommon) }
        index++
    }

    return rating
}
