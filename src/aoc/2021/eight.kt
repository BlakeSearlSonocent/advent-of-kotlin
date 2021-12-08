package aoc.`2021`

import util.readLines

fun main() {
    val patternAndOutput = readLines("2021.8.txt").map { it.split(" | ") }.map { (patterns, output) ->
        patterns.split(" ").map { it.toSet() } to output.split(" ").map { it.toSet() }
    }

    println(patternAndOutput.sumOf { (_, output) -> output.count { it.size in setOf(2, 3, 4, 7) } })

    figureOutOverlaps()

    val sum = patternAndOutput.sumOf { (patterns, output) ->
        val displayWiring = mutableMapOf<Int, Set<Char>>()
        displayWiring[1] = patterns.first { it.size == 2 }
        displayWiring[4] = patterns.first { it.size == 4 }
        displayWiring[7] = patterns.first { it.size == 3 }
        displayWiring[8] = patterns.first { it.size == 7 }
        displayWiring[2] = patterns.filter { it.size == 5 }.first { it.intersect(displayWiring[4]!!).size == 2 }
        displayWiring[3] = patterns.filter { it.size == 5 }.first { it.intersect(displayWiring[7]!!).size == 3 }
        displayWiring[5] = patterns.filter { it.size == 5 }.first { it !in displayWiring.values }
        displayWiring[6] = patterns.filter { it.size == 6 }.first { it.intersect(displayWiring[1]!!).size == 1 }
        displayWiring[9] = patterns.filter { it.size == 6 }.first { it.intersect(displayWiring[4]!!).size == 4 }
        displayWiring[0] = patterns.filter { it.size == 6 }.first { it !in displayWiring.values }

        val patternToNumber = displayWiring.entries.associateBy({ it.value }) { it.key }
        output.map { patternToNumber[it] }.joinToString("").toInt()
    }

    println(sum)
}

private fun figureOutOverlaps() {
    val originalWiring = mutableMapOf<Int, String>()
    originalWiring[0] = "abcefg"
    originalWiring[1] = "cf"
    originalWiring[2] = "acdeg"
    originalWiring[3] = "acdfg"
    originalWiring[4] = "bcdf"
    originalWiring[5] = "abdfg"
    originalWiring[6] = "abdefg"
    originalWiring[7] = "acf"
    originalWiring[8] = "abcdefg"
    originalWiring[9] = "abcdfg"

    val lengthSixes = originalWiring.filter { it.value.length == 6 }
    for (wiring in lengthSixes) {
        for (otherWiring in originalWiring.filter { it.key in setOf(1, 7, 4, 8) }) {
            val overlapSize = wiring.value.filter { it in otherWiring.value }.length
            println("Wiring ${wiring.key} overlaps with ${otherWiring.key} with $overlapSize segments")
        }
        println()
    }

    val lengthFives = originalWiring.filter { it.value.length == 5 }
    for (wiring in lengthFives) {
        for (otherWiring in originalWiring.filter { it.key in setOf(1, 7, 4, 8) }) {
            val overlapSize = wiring.value.filter { it in otherWiring.value }.length
            println("Wiring ${wiring.key} overlaps with ${otherWiring.key} with $overlapSize segments")
        }
        println()
    }
}
