package day11

import Point
import plus
import util.readFileToLines

val DIRECTIONS = listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
var GRID: MutableMap<Point, Char> = mutableMapOf()
var HEIGHT = 0
var WIDTH = 0

fun main() {
    init()

    println("Part one: ${findStability(true, 4)}")
    println("Part two: ${findStability()}")
}

private fun init() {
    val lines = readFileToLines("src/day11/resources/Day11.txt")
    HEIGHT = lines.size
    WIDTH = lines.first().length

    GRID = mutableMapOf()
    for ((iy, line) in lines.withIndex()) {
        for ((ix, x) in line.withIndex()) {
            GRID[Pair(ix, iy)] = x
        }
    }
}

fun findStability(useImmediateNeighbours: Boolean = false, occupiedThreshold: Int = 5): Int {
    var now = GRID.toMutableMap()
    val mutated = GRID.toMutableMap()

    while (true) {
        var changed = false

        for (point in now) {
            if (point.value == '.') continue

            val neighbours = getNeighbours(useImmediateNeighbours, point, now)
            val occupied = neighbours.count { it.value == '#' }

            if (point.value == 'L' && occupied == 0) {
                mutated[point.key] = '#'
                changed = true
            }

            if (point.value == '#' && occupied >= occupiedThreshold) {
                mutated[point.key] = 'L'
                changed = true
            }
        }

        now = mutated.toMutableMap()

        if (!changed) return mutated.count { it.value == '#' }
    }
}

private fun getNeighbours(
    useImmediateNeighbours: Boolean,
    point: MutableMap.MutableEntry<Point, Char>,
    now: MutableMap<Point, Char>
): MutableMap<Point, Char> {
    val neighbours = mutableMapOf<Point, Char>()

    for (direction in DIRECTIONS) {
        var testPointCoords = direction + point.key
        while (testPointCoords.first in 0 until WIDTH
            && testPointCoords.second in 0 until HEIGHT) {
            if (useImmediateNeighbours) {
                neighbours[testPointCoords] = now.getValue(testPointCoords)
                break
            } else {
                val potential = now.getValue(testPointCoords)
                if (potential != '.') {
                    neighbours[testPointCoords] = potential
                    break
                } else {
                    testPointCoords += direction
                }
            }
        }
    }

    return neighbours
}
