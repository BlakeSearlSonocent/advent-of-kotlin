package aoc.`2021`

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
    val lowPoints: List<Pair<Pair<Int, Int>, Int>> = heights.foldIndexed(emptyList()) { ix, cumulative, row ->
        row.foldIndexed(cumulative) { iy, rowPoints, height ->
            if (isLowPoint(ix, iy)) rowPoints + ((ix to iy) to height) else rowPoints
        }
    }

    println(lowPoints.sumOf { it.second + 1 })

    println(
        lowPoints.map { it.first }.map { (x, y) -> findBasin(x, y).size }.sortedBy { it }.takeLast(3)
            .reduce { now, next -> now * next }
    )
}

fun isLowPoint(x: Int, y: Int) =
    getNeighbours(x, y).all { (xTest, yTest) -> heights[xTest][yTest] > heights[x][y] }

fun getNeighbours(x: Int, y: Int): List<Pair<Int, Int>> =
    listOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (deltaX, deltaY) -> (x + deltaX) to (y + deltaY) }
        .filter { (x, y) ->
            x in 0 until width && y in 0 until height
        }

fun findBasin(x: Int, y: Int, checkedPoints: MutableSet<Pair<Int, Int>> = mutableSetOf()): MutableSet<Pair<Int, Int>> {
    val basin = mutableSetOf<Pair<Int, Int>>()

    if (x to y in checkedPoints) return basin

    if (heights[x][y] == 9) {
        checkedPoints += x to y
        return basin
    }

    basin += x to y
    checkedPoints += x to y

    getNeighbours(x, y).forEach { (neighbourX, neighbourY) ->
        if (heights[neighbourX][neighbourY] > heights[x][y]) {
            basin += findBasin(neighbourX, neighbourY, checkedPoints)
        }
    }

    return basin
}
