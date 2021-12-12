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
        when {
            cave.isMajorCave() -> true
            cave == "start" -> false
            cave !in currentPath -> true
            else -> currentPath.filter { it.isMinorCave() }.groupingBy { it }.eachCount().none { it.value > 1 }
        }

    private fun satisfiesPartOneRule(cave: Cave, currentPath: List<Cave>) = cave.isMajorCave() || cave !in currentPath

    private fun findPaths(
        passesCaveRules: (cave: Cave, currentPath: List<Cave>) -> Boolean,
        currentPath: List<Cave> = listOf("start")
    ): List<List<Cave>> =
        if (currentPath.last() == "end") listOf(currentPath)
        else {
            system.getValue(currentPath.last()).filter { passesCaveRules(it, currentPath) }
                .flatMap { findPaths(passesCaveRules, currentPath + it) }
        }

    fun partOne() = findPaths(::satisfiesPartOneRule)
    fun partTwo() = findPaths(::satisfiesPartTwoRule)
}
