package aoc.`2021`

import P
import util.readDoubleLineSeparatedFileToGroups
import kotlin.math.abs

fun main() {
    val (paper, folds) = readDoubleLineSeparatedFileToGroups("2021.13.txt")
    val holes = paper.split('\n').map {
        val (x, y) = it.split(",")
        P(x.toInt(), y.toInt())
    }.toSet()

    val foldCommands = folds.split('\n')
    val codes = foldCommands.runningFold(holes) { acc, next -> acc.map { foldPoint(it, next) }.toSet() }

    println(codes[1].size)
    printPaper(codes.last())
}

fun foldPoint(point: P, foldCommand: String): P {
    val (axis, mirrorLocation) = foldCommand.split("=").let { it.first() to it[1].toInt() }
    val (currentX, currentY) = point

    return when {
        axis.startsWith("fold along y") -> P(currentX, mirrorLocation - abs(currentY - mirrorLocation))
        else -> P(mirrorLocation - abs(currentX - mirrorLocation), currentY)
    }
}

fun printPaper(holes: Set<P>) {
    val xMax = holes.maxOf { it.first }
    val yMax = holes.maxOf { it.second }

    (0..yMax).forEach { y ->
        (0..xMax).forEach { x ->
            val printCharacter = if (P(x, y) in holes) "⬜" else "\uD83D\uDC15"
            print(printCharacter)
        }
        println()
    }
}
