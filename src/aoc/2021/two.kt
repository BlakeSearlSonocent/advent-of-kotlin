package aoc.`2021`

import P
import plus
import util.readLines

fun main() {
    val depths = readLines("2021.2.txt")
    val commands = depths.map {
        val split = it.split(' ')
        split.first() to split[1].toInt()
    }

    val finalPos = commands.fold(Pair(P(0, 0), 0)) { currentPositionAndAim, command ->
        val (commandStr, units) = command
        val movement = when (commandStr) {
            "forward" -> Pair(P(units, currentPositionAndAim.second * units), 0)
            "up" -> Pair(P(0, 0), -units)
            "down" -> Pair(P(0, 0), units)
            else -> Pair(P(0, 0), 0)
        }

        println(movement)

        Pair(currentPositionAndAim.first + movement.first, currentPositionAndAim.second + movement.second)
    }

    println(finalPos)
    println(finalPos.first.first * finalPos.first.second)
}
