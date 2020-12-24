package day24

import P
import Vec
import plus
import util.readFileToLines

private val East = Vec(2, 0)
private val SouthEast = Vec(1, -1)
private val SouthWest = Vec(-1, -1)
private val West = Vec(-2, 0)
private val NorthWest = Vec(-1, 1)
private val NorthEast = Vec(1, 1)

private val ALL_DIRECTIONS = listOf(East, SouthEast, SouthWest, West, NorthWest, NorthEast)

fun main() {
    val instructions = readFileToLines("resources/Day24.txt").map { line ->
        line.replace("ne", "6")
            .replace("se", "2")
            .replace("sw", "3")
            .replace("nw", "5")
            .replace("e", "1")
            .replace("w", "4")
            .map { direction ->
                when (direction) {
                    '1' -> East
                    '2' -> SouthEast
                    '3' -> SouthWest
                    '4' -> West
                    '5' -> NorthWest
                    '6' -> NorthEast
                    else -> error("Something went wrong")
                }
            }
    }

    val map = instructions
        .map { it.reduce { acc, pair -> acc + pair } }
        .groupingBy { it }.eachCount()
        .map { Tile(it.key) to when (it.value % 2) {
                0 -> false
                else -> true
            }
        }.toMap()

    println("Part one: ${map.count { it.value }}")
}

class Tile(position: Vec) {
    val neighbours = ALL_DIRECTIONS.map { it + position }
}
