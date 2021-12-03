package aoc.`2020`

import Vec
import plus
import util.readLines

private val East = Vec(2, 0)
private val SouthEast = Vec(1, -1)
private val SouthWest = Vec(-1, -1)
private val West = Vec(-2, 0)
private val NorthWest = Vec(-1, 1)
private val NorthEast = Vec(1, 1)

private val ALL_DIRECTIONS = listOf(East, SouthEast, SouthWest, West, NorthWest, NorthEast)

fun main() {
    val instructions = readLines("2020.24.txt").map { line ->
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

    var blackTiles = instructions
        .map { it.reduce { acc, pair -> acc + pair } }
        .groupingBy { it }.eachCount()
        .filterNot { it.value % 2 == 0 }
        .map { Tile(it.key) }.toSet()

    println("Part one: ${blackTiles.size}")

    repeat(100) {
        blackTiles = doFlips(blackTiles)
    }

    println("Part two: ${blackTiles.size}")
}

data class Tile(private val position: Vec) {
    val neighbours by lazy { ALL_DIRECTIONS.map { Tile(it + position) } }
}

fun doFlips(black: Set<Tile>): MutableSet<Tile> {
    val newBlack = black.toMutableSet()

    val allPotentialTiles = black.toMutableSet()
    newBlack.forEach { allPotentialTiles.addAll(it.neighbours) }

    for (tile in allPotentialTiles) {
        val isBlack = black.contains(tile)
        val blackNeighbours = (tile.neighbours intersect black).size

        if (isBlack && (blackNeighbours == 0 || blackNeighbours > 2)) newBlack.remove(tile)
        if (!isBlack && blackNeighbours == 2) newBlack.add(tile)
    }

    return newBlack
}

