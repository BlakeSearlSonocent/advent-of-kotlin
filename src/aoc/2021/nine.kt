package aoc.`2021`

import P
import neighbours
import util.readLines

val input = readLines("2021.9.txt")
val width = input[0].length
val height = input.size
val heights = Array(width) { Array(height) { 0 } }.apply {
    input.forEachIndexed { iy, row ->
        row.forEachIndexed { ix, height ->
            this[ix][iy] = height.digitToInt()
        }
    }
}

fun main() {
    val lowPoints: List<Pair<P, Int>> = heights.foldIndexed(emptyList()) { ix, cumulative, row ->
        row.foldIndexed(cumulative) { iy, rowPoints, height ->
            if (isLowPoint(ix to iy)) rowPoints + ((ix to iy) to height) else rowPoints
        }
    }

    println(lowPoints.sumOf { it.second + 1 })

    println(
        lowPoints.map { it.first }.map { point -> findBasin(point).size }.sortedBy { it }.takeLast(3)
            .reduce { now, next -> now * next }
    )
}

fun isLowPoint(point: P) =
    getNeighbours(point).all { (xTest, yTest) -> heights[xTest][yTest] > heights[point.first][point.second] }

fun getNeighbours(point: P): List<Pair<Int, Int>> =
    point.neighbours().filter { (x, y) -> x in 0 until width && y in 0 until height }

fun findBasin(point: P, checkedPoints: MutableSet<P> = mutableSetOf()): MutableSet<P> {
    val basin = mutableSetOf<Pair<Int, Int>>()

    if (point in checkedPoints) return basin

    val (x, y) = point
    val height = heights[x][y]
    if (height == 9) {
        checkedPoints += point
        return basin
    }

    basin += point
    checkedPoints += point

    getNeighbours(point).forEach { (neighbourX, neighbourY) ->
        if (heights[neighbourX][neighbourY] > height) {
            basin += findBasin(neighbourX to neighbourY, checkedPoints)
        }
    }

    return basin
}
