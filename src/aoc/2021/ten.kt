package aoc.`2021`

import util.readLines

fun main() {
    val lines = readLines("2021.10.txt")

    println(lines.sumOf { scoreLine(it).first })
    println(lines.map { scoreLine(it) }.filter { it.first == 0 }.sortedBy { it.second }.let { it[it.size / 2] }.second)
}

fun scoreLine(line: String): Pair<Int, Long> {
    val nonMatchedChars = mutableListOf<Char>()

    for (char in line) {
        when (char) {
            ')' -> if (nonMatchedChars.firstOrNull() == '(') nonMatchedChars.removeAt(0) else return 3 to 0L
            ']' -> if (nonMatchedChars.firstOrNull() == '[') nonMatchedChars.removeAt(0) else return 57 to 0L
            '}' -> if (nonMatchedChars.firstOrNull() == '{') nonMatchedChars.removeAt(0) else return 1197 to 0L
            '>' -> if (nonMatchedChars.firstOrNull() == '<') nonMatchedChars.removeAt(0) else return 25137 to 0L
            else -> nonMatchedChars.add(0, char)
        }
    }

    return 0 to nonMatchedChars.fold(0L) { acc, char ->
        val nextVal = when (char) {
            '(' -> 1L
            '[' -> 2L
            '{' -> 3L
            '>' -> 4L
            else -> 0
        }

        acc * 5 + nextVal
    }
}
