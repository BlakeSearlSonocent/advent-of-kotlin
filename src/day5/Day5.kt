package day5

import util.readFileToLines
import kotlin.math.pow

fun main() {
    val input = readFileToLines("src/day5/resources/Day5.txt")
    println("Part one: ${input.map { Barcode(it).decodePartOne() }.max()}")

    val seatsWithOneGap = input
        .map { Barcode(it).decodePartOne() }
        .sorted()
        .zipWithNext()
        .filter { it.second - it.first == 2 }

    print("Part two: ${seatsWithOneGap.first().first + 1}")
}

class Barcode(private val binaryPartitioning: String) {
    fun decodePartOne(): Int {
        return binaryPartitioning.replace("F", "0")
            .replace("B", "1")
            .replace("L", "0")
            .replace("R", "1")
            .let { listOf(it.substring(0, 7), it.substring(7, 10)) }
            .map { it.toInt() }
            .map { toDecimal(it) }
            .let { (it[0] * 8) + it[1]}
    }
}

fun toDecimal(binary: Int): Int {
    var binary = binary
    var ix = 0
    var decimal = 0

    while (binary > 0) {
        val remainder = binary % 10
        decimal += (remainder * 2.0.pow(ix.toDouble()).toInt())

        binary /= 10
        ix++
    }

    return decimal
}
