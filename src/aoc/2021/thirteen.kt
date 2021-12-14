package aoc.`2021`

import P
import util.readDoubleLineSeparatedFileToGroups
import kotlin.math.abs

fun Int.inflectAt(x: Int) = x - abs(this - x)

fun main() {
    val (paper, folds) = readDoubleLineSeparatedFileToGroups("2021.13.txt")
    val holes = paper.split('\n').map { it.split(",") }.map { P(it.first().toInt(), it.last().toInt()) }.toSet()

    val papers = folds.split('\n').map { it.split("=") }
        .map { if (it.first().endsWith("y")) P(0, it.last().toInt()) else P(it.last().toInt(), 0) }
        .runningFold(holes) { acc, next -> acc.map { foldPoint(it, next) }.toSet() }

    println(papers[1].size)
    papers.forEach(::printPaper)
}

fun foldPoint(point: P, mirrorLocation: P): P =
    if (mirrorLocation.first > 0) P(point.first.inflectAt(mirrorLocation.first), point.second)
    else P(point.first, point.second.inflectAt(mirrorLocation.second))

fun printPaper(holes: Set<P>) {
    (0..holes.maxOf { it.second }).forEach { y ->
        (0..holes.maxOf { it.first }).forEach { x ->
            print(if (P(x, y) in holes) "⬜" else "\uD83D\uDC15")
        }
        println()
    }

    println()
    println()
}
