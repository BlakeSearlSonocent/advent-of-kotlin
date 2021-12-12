package aoc.`2021`

import util.readLines

typealias Cave = String

fun Cave.isMinorCave() = this.lowercase() == this
fun Cave.isMajorCave() = !this.isMinorCave()

fun main() {
    val neighbours = readLines("2021.12.txt").map { it.split("-") }
        .fold(mutableMapOf<Cave, Set<Cave>>()) { acc, (first, second) ->
            acc[first] = acc.getOrDefault(first, emptySet()) + second
            acc[second] = acc.getOrDefault(second, emptySet()) + first
            acc
        }.toMap()

    val caveSystem = CaveSystem(neighbours)
    println("Part one: ${caveSystem.partOne().size}")
    println("Part two: ${caveSystem.partTwo().size}")
}

class CaveSystem(private val system: Map<Cave, Set<Cave>>) {
    private fun satisfiesPartTwoRule(cave: Cave, currentPath: List<Cave>) =
        cave !in currentPath || currentPath.filter { it.isMinorCave() }.groupingBy { it }.eachCount()
            .none { it.value > 1 }

    private fun satisfiesPartOneRule(cave: Cave, currentPath: List<Cave>) = cave !in currentPath

    private fun populatePaths(
        passesSmallCaveRule: (cave: Cave, currentPath: List<Cave>) -> Boolean,
        start: Cave = "start",
        currentPath: List<Cave> = emptyList()
    ): List<List<Cave>> {
        if (start == "end") {
            return listOf(currentPath + start)
        } else if ((start.isMinorCave() && passesSmallCaveRule(start, currentPath)) || start.isMajorCave()) {
            return system.getValue(start).filterNot { it == "start" }
                .flatMap { populatePaths(passesSmallCaveRule, it, currentPath + start) }
        }

        return emptyList()
    }

    fun partOne() = populatePaths(::satisfiesPartOneRule)
    fun partTwo() = populatePaths(::satisfiesPartTwoRule)
}
