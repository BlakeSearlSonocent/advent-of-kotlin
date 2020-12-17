package day17

import util.readFileToLines
import kotlin.math.abs

fun main() {
    var alivePoints = readFileToLines("resources/Day17.txt").mapIndexed { yIndex, line ->
        line.mapIndexed { xIndex, value ->
            Point(
                xIndex,
                yIndex,
                0,
                0
            ) to value
        }
    }.flatten().filter { it.second == '#' }.toMap()

    println(alivePoints)

    for (i in 1..6) {
        alivePoints = performCycle(alivePoints)
    }

    println(alivePoints.count())
}

fun performCycle(grid: Grid): MutableMap<Point, Char> {
    val newGrid = mutableMapOf<Point, Char>()

    val (x, y, z, t) = grid.getMaxDimensions()
    for (ix in -x..x) {
        for (iy in -y..y) {
            for (iz in -z..z) {
                for (it in -t..t) {
                    val testPoint = Point(ix, iy, iz, it)
                    val c = grid[testPoint]
                    val neighbours = testPoint.getNeighbours()

                    val activeNeighbourCount =
                        neighbours.map { neighbour -> neighbour to grid.getOrDefault(neighbour, '.') }
                            .count { it.second == '#' }
                    if (c == null) {
                        if (activeNeighbourCount == 3) newGrid[testPoint] = '#'
                    } else {
                        if (activeNeighbourCount in listOf(2, 3)) newGrid[testPoint] = '#'
                    }
                }
            }
        }
    }

    return newGrid
}

private fun Map<Point, Char>.getMaxDimensions(): Point {
    val points = this.keys
    return Point(abs(points.maxBy { abs(it.x) }!!.x) + 1,abs(points.maxBy { abs(it.y) }!!.y) + 1, abs(points.maxBy { abs(it.z) }!!.z) + 1, abs(points.maxBy { abs(it.t) }!!.t) + 1)
}


typealias Grid = Map<Point, Char>

data class Point(val x: Int, val y: Int, val z: Int, val t: Int) {
    fun getNeighbours(): List<Point> {
        val neighbours = mutableListOf<Point>()
        val perturbations = listOf(-1, 0, 1)

        for (xPerturb in perturbations) {
            for (yPerturb in perturbations) {
                for (zPerturb in perturbations) {
                    for (tPerturb in perturbations) {
                        val potentialNeighbour = Point(x + xPerturb, y + yPerturb, z + zPerturb, t + tPerturb)
                        if (potentialNeighbour != this) neighbours.add(potentialNeighbour)
                    }
                }
            }
        }

        return neighbours
    }
}