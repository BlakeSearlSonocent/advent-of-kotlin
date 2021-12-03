package util

import kotlin.math.pow

fun toDecimal(binary: Long): Long {
    var binary = binary
    var ix = 0
    var decimal = 0L

    while (binary > 0) {
        val remainder = binary % 10
        decimal += (remainder * 2.0.pow(ix.toDouble()).toInt())

        binary /= 10
        ix++
    }

    return decimal
}

fun Long.toDigits(): List<Int> = toString().map { it.toString().toInt() }
