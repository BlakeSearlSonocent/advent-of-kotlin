package aoc.`2021`

import util.readLines

fun main() {
    val lineStr = readLines("2021.5.txt")
    val lines = lineStr.map {
        it.split(" -> ").map { coord -> coord.split(",") }
            .map { coordStr -> coordStr.first().toInt() to coordStr[1].toInt() }
    }

    val visitedCoords = mutableMapOf<Pair<Int, Int>, Int>()

    lines.forEach { (start, finish) ->
        val (x1, y1) = start
        val (x2, y2) = finish
        val xIndices = if (x1 > x2) x2..x1 else x1..x2
        val yIndices = if (y1 > y2) y2..y1 else y1..y2
        if (x1 == x2) {
            (yIndices).map { Pair(x1, it) }
                .forEach { visit -> visitedCoords.putOrIncrease(visit) }
        } else if (y1 == y2) {
            (xIndices).map { Pair(it, y1) }
                .forEach { visit -> visitedCoords.putOrIncrease(visit) }
        } else {
            var x = x1
            var y = y1
            while (true) {
                visitedCoords.putOrIncrease(x to y)
                if (x == x2) {
                    break
                } else {
                    if (x2 > x1) x++ else x--
                    if (y2 > y1) y++ else y--
                }
            }
        }
    }

    println(visitedCoords.count { it.value > 1 })
}

fun MutableMap<Pair<Int, Int>, Int>.putOrIncrease(coord: Pair<Int, Int>) {
    val count = getOrDefault(coord, 0)
    this[coord] = count + 1
}
