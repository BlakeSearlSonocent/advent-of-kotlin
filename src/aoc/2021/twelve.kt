package aoc.`2021`

import util.readLines

fun main() {
    val joins = readLines("2021.12.txt").map { it.split("-") }.map { it[0] to it[1] }

    val neighbours = mutableMapOf<String, Set<String>>()
    for ((first, second) in joins) {
        neighbours[first] = neighbours.getOrDefault(first, emptySet()) + second
        neighbours[second] = neighbours.getOrDefault(second, emptySet()) + first
    }

    val caveSystem = CaveSystem(neighbours)
    caveSystem.partOne()
    caveSystem.partTwo()
}

class CaveSystem(private val system: Map<String, Set<String>>) {
    private fun satisfiesPartTwoRule(cave: String, currentPath: List<String>) =
        cave !in currentPath || currentPath.filter { it.lowercase() == it }.groupingBy { it }.eachCount()
            .none { it.value > 1 }

    private fun satisfiesPartOneRule(cave: String, currentPath: List<String>) = cave !in currentPath

    private fun populatePaths(
        start: String,
        currentPath: List<String>,
        paths: MutableSet<List<String>>,
        passesSmallCaveRule: (cave: String, currentPath: List<String>) -> Boolean
    ) {
        if (start == "end") {
            val finalPath = currentPath + start
            paths += finalPath
        } else if ((
            start.lowercase() == start && passesSmallCaveRule(
                    start,
                    currentPath
                )
            ) || start.uppercase() == start
        ) {
            val updatedPath = currentPath + start
            system.getValue(start).filterNot { it == "start" }
                .forEach { populatePaths(it, updatedPath, paths, passesSmallCaveRule) }
        }
    }

    fun partOne() {
        val paths = mutableSetOf<List<String>>()
        populatePaths("start", mutableListOf(), paths, ::satisfiesPartOneRule)
        println("Part one: ${paths.size}")
    }

    fun partTwo() {
        val paths = mutableSetOf<List<String>>()
        populatePaths("start", mutableListOf(), paths, ::satisfiesPartTwoRule)
        println("Part two: ${paths.size}")
    }
}
