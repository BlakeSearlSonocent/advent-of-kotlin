package day20

import util.readFileToText

val REGEX_TILE_ID = """([0-9]+)""".toRegex()

fun main() {
    val input = readFileToText("resources/Day20.txt")
    val tiles = input.split("\n\n").map { it.split("\n") }
        .map { Tile(REGEX_TILE_ID.find(it.first())!!.value.toLong(), it.drop(1)) }

    println("Part one: ${tiles.map { it to it.getPotentialNeighbours(tiles) }.filter { it.second.size == 2 }.map { it.first.id }.reduce { acc, it -> acc * it }})
}

data class Tile(val id: Long, val tile: List<String>) {
    private fun getPotentialVerticals(): Set<String> {
        val left = tile.map { it.first() }.joinToString("")
        val leftReversed = left.reversed()
        val right = tile.map { it.last() }.joinToString("")
        val rightReversed = right.reversed()

        return setOf(left, leftReversed, right, rightReversed)
    }

    private fun getPotentialHorizontals() : Set<String> {
        val top = tile.let { it.first() }
        val topReversed = top.reversed()
        val bottom = tile.let { it.last() }
        val bottomReversed = bottom.reversed()

        return setOf(top, topReversed, bottom, bottomReversed)
    }

    private fun getPotentialEdges(): Set<String> {
        return getPotentialVerticals() + getPotentialHorizontals()
    }

    fun getPotentialNeighbours(allTiles: List<Tile>)  = allTiles.filterNot { it.id == this.id }.filter {
            val intersection = it.getPotentialEdges() intersect this.getPotentialEdges()
            intersection.isNotEmpty()
    }
}