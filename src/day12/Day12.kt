package day12

import P
import Vec
import plus
import times
import manhattanDistance
import util.readFileToLines

val REGEX_INSTRUCTION = """([A-Z])(\d+)""".toRegex()
val BEARING_TO_VEC = mapOf(0 to Vec(0, 1), 90 to Vec(1, 0), 180 to Vec(0, -1), 270 to Vec(-1, 0))

fun main() {
    val lines = readFileToLines("src/day12/resources/Day12.txt")
    val map = lines.map { it ->
        val (first, second) = REGEX_INSTRUCTION.matchEntire(it)!!.destructured
        first to second.toInt()
    }

    println(map)
    println("Part one: ${partOne(map)}")
    println("Part two: ${partTwo(map)}")
}

private fun partTwo(map: List<Pair<String, Int>>): Int {
    var wp= P(10, 1)
    var ship = P(0, 0)

    for ((instruct, magnitude) in map) {
        when (instruct) {
            "N" -> wp = wp.copy(wp.first, wp.second + magnitude)
            "E" -> wp = wp.copy(wp.first + magnitude, wp.second)
            "S" -> wp = wp.copy(wp.first, wp.second - magnitude)
            "W" -> wp = wp.copy(wp.first - magnitude, wp.second)
            "R" -> when (magnitude) {
                90 -> wp = P(wp.second, -wp.first)
                180 -> wp = P(-wp.first, -wp.second)
                270 -> wp = P(-wp.second, wp.first)
            }
            "L" -> when (magnitude) {
                90 -> wp = P(-wp.second, wp.first)
                180 -> wp = P(-wp.first, -wp.second)
                270 -> wp = P(wp.second, -wp.first)
            }
            "F" -> {
                ship = ship.copy() + (magnitude * wp)
            }
        }

        println("Waypoint: $wp")
        println("Ship: $ship")
    }

    return ship.manhattanDistance()
}

private fun partOne(map: List<Pair<String, Int>>): Int {
    var bearing = 90
    var position = P(0, 0)

    for ((instruct, magnitude) in map) {
        when (instruct) {
            "N" -> position = position.copy(position.first, position.second + magnitude)
            "E" -> position = position.copy(position.first + magnitude, position.second)
            "S" -> position = position.copy(position.first, position.second - magnitude)
            "W" -> position = position.copy(position.first - magnitude, position.second)
            "R" -> bearing = (bearing + magnitude) % 360
            "L" -> bearing = (bearing + (360 - magnitude)) % 360
            "F" -> {
                val vec = BEARING_TO_VEC[bearing] ?: error("Bearing doesn't match a vector")
                position = position.copy() + (magnitude * vec)
            }
        }

        println(position)
    }

    return position.manhattanDistance()
}
