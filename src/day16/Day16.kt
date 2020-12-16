package day16

import java.io.File

val REGEX_RULE = """(\d+)-(\d+)""".toRegex()
val REGEX_NUMERICS = """(\d+)""".toRegex()

fun main () {
    val input = File("resources/Day16.txt").readText().split("\n\n")
    val nearbyTickets = REGEX_NUMERICS.findAll(input[2]).map { it.value.toInt() }.toList()

    val rules = REGEX_RULE.findAll(input.first()).toList().map { it.value }
    val ranges = rules.map { REGEX_NUMERICS.findAll(it).toList().map { it.value.toInt() } }.map { it[0]..it[1] }

    val notInAny = nearbyTickets.filter {
        var inRange = false
        ranges.forEach { range ->
            if (it in range) {
                inRange = true
            }
        }

        !inRange
    }

    println(notInAny.sum())
}