package aoc.`2021`

import P
import neighboursIncludingDiagonals
import util.readLines

fun main() {
    val grid = readLines("2021.11.txt").flatMapIndexed { iy, row ->
        row.mapIndexed { ix, level -> P(ix, iy) to level.digitToInt() }
    }.toMap().toMutableMap()

    var hasSynchronized = false
    var stepCount = 0
    var flashCount = 0
    while (!hasSynchronized) {
        stepCount++

        val flashers = step(grid)
        flashCount += flashers.size

        if (stepCount == 100) {
            println("Part one $flashCount")
        }

        if (flashers.size == 100) {
            println("Part two $stepCount")
            hasSynchronized = true
        }
    }
}

private fun step(grid: MutableMap<P, Int>): MutableSet<P> {
    grid.forEach { (k, v) -> grid[k] = v + 1 }

    val flashers = mutableSetOf<P>()

    do {
        val newFlashers = grid.filter { (k, v) -> v > 9 && k !in flashers }

        flashers += newFlashers.keys

        for (flasher in newFlashers) {
            val neighbours = getNeighbours(flasher.key, grid)
            neighbours.forEach { (k, v) -> grid[k] = v + 1 }
        }
    } while (newFlashers.isNotEmpty())

    grid.filter { (_, v) -> v > 9 }.forEach { (k, _) -> grid[k] = 0 }

    return flashers
}

private fun getNeighbours(position: P, grid: MutableMap<Pair<Int, Int>, Int>): List<Pair<P, Int>> {
    val neighbours = position.neighboursIncludingDiagonals().filter { (x, y) -> x in 0..9 && y in 0..9 }
    return neighbours.map { it to grid[it]!! }
}
