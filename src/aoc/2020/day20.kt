package aoc.`2020`

import util.readFileToText
import kotlin.math.sqrt

val tiles = parseInput(readFileToText("2020.20.txt"))

fun main() {
    val image: List<List<ATile>> = createImage()

    val partOne = image.first().first().id * image.first().last().id * image.last().first().id * image.last().last().id
    println(partOne)

    imageToSingleTile(image)
}

private fun imageToSingleTile(image: List<List<ATile>>): ATile {
    val rowsPerTile = tiles.first().body.size
    val body = image.flatMap { row ->
        (1 until rowsPerTile - 1).map { y ->
            row.joinToString("") { it.insetRow(y) }.toCharArray()
        }
    }.toTypedArray()
    return ATile(0, body)
}

private fun findTopCorner(): ATile =
    tiles
        .first { tile -> tile.sharedSideCount(tiles) == 2 }
        .orientations()
        .first {
            it.isSideShared(Orientation.South, tiles) && it.isSideShared(Orientation.East, tiles)
        }

private fun createImage(): List<List<ATile>> {
    val width = sqrt(tiles.count().toFloat()).toInt()
    var mostRecentTile: ATile = findTopCorner()
    var mostRecentRowHeader: ATile = mostRecentTile
    return (0 until width).map { row ->
        (0 until width).map { col ->
            when {
                row == 0 && col == 0 ->
                    mostRecentTile
                col == 0 -> {
                    mostRecentRowHeader =
                        mostRecentRowHeader.findAndOrientNeighbor(Orientation.South, Orientation.North, tiles)
                    mostRecentTile = mostRecentRowHeader
                    mostRecentRowHeader
                }
                else -> {
                    mostRecentTile =
                        mostRecentTile.findAndOrientNeighbor(Orientation.East, Orientation.West, tiles)
                    mostRecentTile
                }
            }
        }
    }
}

class ATile(val id: Long, var body: Array<CharArray>) {
    private val sides: Set<String> = Orientation.values().map { sideFacing(it) }.toSet()
    private val sidesReversed = sides.map { it.reversed() }.toSet()

    private fun hasSide(side: String): Boolean = side in sides || side in sidesReversed

    private fun flip(): ATile {
        body = body.map { it.reversed().toCharArray() }.toTypedArray()
        return this
    }

    private fun rotateClockwise(): ATile {
        body = body.mapIndexed { x, row ->
            row.mapIndexed { y, _ ->
                body[y][x]
            }.reversed().toCharArray()
        }.toTypedArray()
        return this
    }

    fun orientations(): Sequence<ATile> = sequence {
        repeat(2) {
            repeat(4) {
                yield(this@ATile.rotateClockwise())
            }
            this@ATile.flip()
        }
    }

    fun sharedSideCount(tiles: List<ATile>): Int =
        sides.sumBy { side ->
            tiles
                .filterNot { it.id == id }
                .count { tile -> tile.hasSide(side) }
        }

    fun isSideShared(dir: Orientation, tiles: List<ATile>): Boolean =
        tiles
            .filterNot { it.id == id }
            .any { tile -> tile.hasSide(sideFacing(dir)) }

    fun findAndOrientNeighbor(mySide: Orientation, theirSide: Orientation, tiles: List<ATile>): ATile {
        val mySideValue = sideFacing(mySide)
        return tiles
            .filterNot { it.id == id }
            .first { it.hasSide(mySideValue) }
            .also { it.orientToSide(mySideValue, theirSide) }
    }

    private fun orientToSide(side: String, direction: Orientation) =
        orientations().first { it.sideFacing(direction) == side }

    private fun sideFacing(dir: Orientation): String =
        when (dir) {
            Orientation.North -> body.first().joinToString("")
            Orientation.South -> body.last().joinToString("")
            Orientation.West -> body.map { row -> row.first() }.joinToString("")
            Orientation.East -> body.map { row -> row.last() }.joinToString("")
        }

    fun insetRow(row: Int): String =
        body[row].drop(1).dropLast(1).joinToString("")
}

enum class Orientation {
    North, East, South, West
}

private fun parseInput(input: String): List<ATile> =
    input.split("\n\n").map { it.lines() }.map { line ->
        val id = line.first().substringAfter(" ").substringBefore(":").toLong()
        val body = line.drop(1).map { it.toCharArray() }.toTypedArray()
        ATile(id, body)
    }

// data class aoc.`2020`.Tile(val id: Long, val tile: List<String>) {
//    private fun getPotentialVerticals(): Set<String> {
//        val left = tile.map { it.first() }.joinToString("")
//        val leftReversed = left.reversed()
//        val right = tile.map { it.last() }.joinToString("")
//        val rightReversed = right.reversed()
//
//        return setOf(left, leftReversed, right, rightReversed)
//    }
//
//    private fun getPotentialHorizontals() : Set<String> {
//        val top = tile.let { it.first() }
//        val topReversed = top.reversed()
//        val bottom = tile.let { it.last() }
//        val bottomReversed = bottom.reversed()
//
//        return setOf(top, topReversed, bottom, bottomReversed)
//    }
//
//    private fun getPotentialEdges(): Set<String> {
//        return getPotentialVerticals() + getPotentialHorizontals()
//    }
//
//    fun getPotentialNeighbours(allTiles: List<aoc.`2020`.Tile>)  = allTiles.filterNot { it.id == this.id }.filter {
//            val intersection = it.getPotentialEdges() intersect this.getPotentialEdges()
//            intersection.isNotEmpty()
//    }
// }
