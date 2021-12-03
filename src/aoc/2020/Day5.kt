import util.readLines
import util.toDecimal

fun main() {
    val input = readLines("2020.5.txt")
    println("Part one: ${input.map { Barcode(it).decodePartOne() }.maxOrNull()}")

    val seatsWithOneGap = input
        .map { Barcode(it).decodePartOne() }
        .sorted()
        .zipWithNext()
        .filter { it.second - it.first == 2L }

    print("Part two: ${seatsWithOneGap.first().first + 1}")
}

class Barcode(private val binaryPartitioning: String) {
    fun decodePartOne(): Long {
        return binaryPartitioning.replace("F", "0")
            .replace("B", "1")
            .replace("L", "0")
            .replace("R", "1")
            .let { listOf(it.substring(0, 7), it.substring(7, 10)) }
            .map { it.toInt() }
            .map { toDecimal(it.toLong()) }
            .let { (it[0] * 8) + it[1] }
    }
}
