package day2

import util.readFileToLines


class RuleAndPassword(val low: Int, val high: Int, val letter: Char, val password: String) {
    fun partOne() = password.count { it == letter } in low..high
    fun partTwo() = listOf(low, high).count { password[it - 1] == letter } == 1
}

fun readLine(line: String) = Regex("(\\d\\d?)-(\\d\\d?) (.): (.*)").find(line)

fun main() {
    val rulesAndPws = readFileToLines("src/day2/resources/Day2.txt")
        .map { readLine(it) }
        .map { it!!.destructured }
        .map { (low, high, letter, pw) -> RuleAndPassword(low.toInt(), high.toInt(), letter.first(), pw) }

    println("Part one: ${rulesAndPws.count { it.partOne() }}")
    println("Part two: ${rulesAndPws.count { it.partTwo() }}")
}