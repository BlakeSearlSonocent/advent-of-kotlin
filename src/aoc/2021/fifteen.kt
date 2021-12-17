package aoc.`2021`

import P
import neighbours
import plus
import util.readLines
import java.util.*

val map = readLines("2021.15.txt").flatMapIndexed { iy, row ->
    row.mapIndexed { ix, node -> P(ix, iy) to node.digitToInt() }
}.toMap()
val xMax = map.keys.maxOf { (x, _) -> x }
val yMax = map.keys.maxOf { (_, y) -> y }

fun main() {
    println(dijkstas(map))

    val largeMap = buildMap<P, Int> {
        (0..4).forEach { deltaX ->
            (0..4).forEach { deltaY ->
                map.entries.forEach { (key, value) ->
                    this[key + P(deltaX * (xMax + 1), deltaY * (yMax + 1))] =
                        if (value + deltaX + deltaY > 9) (value + deltaX + deltaY + 1) % 10 else value + deltaX + deltaY
                }
            }
        }
    }

    (0..49).forEach { y ->
        (0..49).forEach { x ->
            print(largeMap.getValue(P(x, y)))
        }
        println()
    }
    println(dijkstas(largeMap))
}

fun P.getNeighbours(map: Map<P, Int>): List<Pair<P, Int>> {
    val xMax = map.keys.maxOf { (x, _) -> x }
    val yMax = map.keys.maxOf { (_, y) -> y }
    return this.neighbours()
        .filter { it.first in 0..xMax && it.second in 0..yMax }
        .map { it to map.getValue(it) }
}

fun dijkstas(grid: Map<P, Int>): Int {
    val xMax = grid.keys.maxOf { (x, _) -> x }
    val yMax = grid.keys.maxOf { (_, y) -> y }

    val completedPoints = mutableSetOf<P>()
    val incomplete = PriorityQueue<Chiton>().apply { add(Chiton(P(0, 0), 0)) }
    while (incomplete.isNotEmpty()) {
        val lowestIncomplete = incomplete.poll()

        if (lowestIncomplete.point == P(xMax, yMax)) {
            return lowestIncomplete.cost
        }

        if (lowestIncomplete.point !in completedPoints) {
            completedPoints.add(lowestIncomplete.point)
            lowestIncomplete.point.getNeighbours(grid).forEach {
                incomplete.offer(Chiton(it.first, lowestIncomplete.cost + grid[it.first]!!))
            }
        }
    }

    return 1
}

class Chiton(val point: P, val cost: Int) : Comparable<Chiton> {
    override fun compareTo(other: Chiton) = this.cost - other.cost
}
