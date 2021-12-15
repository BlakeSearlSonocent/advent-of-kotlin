package aoc.`2021`

import util.readLines

fun String.insert(index: Int, string: String) = this.substring(0, index) + string + this.substring(index, this.length)

fun main() {
    val input = readLines("2021.14.txt")
    val insertionRules =
        input.takeLastWhile { it.isNotEmpty() }.map { it.split(" -> ") }.map { it.first() to it.last() }.toMap()

    val resultingPolymer = (1..10).fold(input.first()) { polymer, _ ->
        polymer.zipWithNext().joinToString("") { (first, second) ->
            "${first}${
            insertionRules.getOrDefault(
                "$first$second",
                ""
            )
            }"
        } + polymer.last()
    }

    println(
        resultingPolymer.groupingBy { it }.eachCount()
            .let { it.maxOf { letter -> letter.value } - it.minOf { letter -> letter.value } }
    )

    val startingPairs =
        input.first().zipWithNext().map { "${it.first}${it.second}" }.groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }.toMutableMap()

    println(startingPairs)

    val thing = (1..40).fold(startingPairs) { acc, _ ->
        buildMap<String, Long> {
            acc.forEach { (pair, count) ->
                val inserter = insertionRules.getValue(pair)
                val firstPolymer = "${pair.first()}$inserter"
                val secondPolymer = "$inserter${pair.last()}"
                this[firstPolymer] = this.getOrDefault(firstPolymer, 0) + count
                this[secondPolymer] = this.getOrDefault(secondPolymer, 0) + count
            }
        }.toMutableMap()
    }

    val lastChar = input.first().last()

    val counts = thing.map { it.key.first() to it.value }.groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() + if (it.key == lastChar) 1 else 0 }

    println(counts.values.sortedByDescending { it }.let { it.first() - it.last() })
}
