package aoc.`2021`

import P
import allNeighbours
import util.readLines

fun main() {
    val input = readLines("2021.20.txt")
    val trenchEnhancement =
        TrenchEnhancer(input.drop(2).map { it.binaryRepresentation() }, input.first().binaryRepresentation())

    val finalImage = (0 until 50).fold(trenchEnhancement to '0') { (enhancer, expanseDefault), _ ->
        enhancer.enhance(expanseDefault) to if (expanseDefault == '0') '1' else '0'
    }

    println(finalImage.first.trench.sumOf { it.count { char -> char == '1' } })
}

typealias Trench = List<String>

private operator fun Trench.contains(point: P) =
    point.first in this.first().indices && point.second in this.indices

private fun Trench.at(point: P, expanseDefault: Char) =
    if (point in this) this[point.second][point.first] else expanseDefault

private fun String.binaryRepresentation() = this.map { if (it == '#') 1 else 0 }.joinToString("")

class TrenchEnhancer(val trench: Trench, private val enhancementAlgo: String) {
    fun enhance(expanseDefault: Char) = TrenchEnhancer(
        (-1..trench.size).map { y ->
            (-1..trench.first().length).map { x ->
                enhancementAlgo[P(x, y).allNeighbours().map { trench.at(it, expanseDefault) }.joinToString("").toInt(2)]
            }.joinToString("")
        },
        enhancementAlgo
    )
}
