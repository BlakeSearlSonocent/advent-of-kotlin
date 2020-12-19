package day18

import asLong
import util.readFileToLines
import java.lang.Character.isDigit

fun main() {
    val lines = readFileToLines("resources/Day18.txt")
    println("Part one: ${lines.map { it.replace(" ", "").iterator() }.map { doMaths(it) }.sum()}")
    println("Part two: ${lines.map { it.replace(" ", "").iterator() }.map { doMathsTwo(it) }.sum()}")
}

fun doMaths(remainder: CharIterator): Long {
    val numbers = mutableListOf<Long>()
    var operation = '+'
    loop@ while (remainder.hasNext()) {
        when (val next = remainder.nextChar()) {
            '(' -> numbers += doMaths(remainder)
            ')' -> break@loop
            in setOf('+', '*') -> operation = next
            else -> numbers += next.asLong()
        }
        if (numbers.size == 2) {
            val first = numbers.removeAt(0)
            val second = numbers.removeAt(0)
            numbers += if (operation == '*') first * second else first + second
        }
    }

    return numbers.first()
}

fun doMathsTwo(remainder: CharIterator): Long {
    val numbers = mutableListOf<Long>()
    var sum = 0L
    loop@ while (remainder.hasNext()) {
        val next = remainder.nextChar()
        when {
            next == '(' -> sum += doMathsTwo(remainder)
            next == ')' -> break@loop
            next == '*' -> {
                numbers += sum
                sum = 0L
            }
            next.isDigit() -> sum += next.asLong()
        }
    }

    return (numbers + sum).reduce { acc, l -> acc * l }
}
