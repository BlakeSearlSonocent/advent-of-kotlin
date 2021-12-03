package aoc.`2021`

import util.readLines
import util.toDecimal

fun main() {
    val binaries = readLines("2021.3.txt")

    val gamma = binaries.fold(IntArray(binaries[0].length)) { acc, next ->
        acc.mapIndexed { i, bit -> bit + next[i].digitToInt() }.toIntArray()
    }.map { if (it > binaries.size / 2) 1 else 0 }.joinToString("")

    val epsilon = gamma.map { 1 - it.digitToInt() }.joinToString("")

    println("Part one: ${toDecimal(epsilon.toLong()) * toDecimal(gamma.toLong())}")

    val indices = binaries.indices
    var oxygen = binaries.toList()
    for (i in indices) {
        val zeroes = oxygen.count { it[i] == '0' }
        val ones = oxygen.count { it[i] == '1' }

        oxygen = if (ones >= zeroes) {
            oxygen.filter { it[i] == '1' }
        } else {
            oxygen.filter { it[i] == '0' }
        }

        if (oxygen.size == 1) break
    }

    var dioxide = binaries.toList()
    for (i in indices) {
        val zeroes = dioxide.count { it[i] == '0' }
        val ones = dioxide.count { it[i] == '1' }

        dioxide = if (zeroes <= ones) {
            dioxide.filter { it[i] == '0' }
        } else {
            dioxide.filter { it[i] == '1' }
        }

        if (dioxide.size == 1) break
    }

    println("Part two: ${toDecimal(oxygen.first().toLong()) * toDecimal(dioxide.first().toLong())}")
}
