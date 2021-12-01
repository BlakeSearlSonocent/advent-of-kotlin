package aoc.`2020`

import util.readFileToText

fun main() {
    val (d1, d2) = readFileToText("2020.22.txt").split("\n\n").map { cards -> cards.split("\n").drop(1).map { it.toInt() }.toMutableList() }

    println("Part one: ${combat(d1.toMutableList(), d2.toMutableList()).second.getScore()}")
    println("Part two: ${recursiveCombat(d1.toMutableList(), d2.toMutableList()).second.getScore()}")
}

private fun MutableList<Int>.getScore() = this.reversed().mapIndexed { index, i -> (index + 1) * i }.sum()

fun combat(d1: MutableList<Int>, d2: MutableList<Int>): Pair<Int, MutableList<Int>> {
    while (d1.isNotEmpty() && d2.isNotEmpty()) {
        val c1 = d1.removeFirst()
        val c2 = d2.removeFirst()

        if (c1 > c2) d1.addAll(listOf(c1, c2)) else d2.addAll(listOf(c2, c1))
    }

    return if (d1.isNotEmpty()) 1 to d1 else 2 to d2
}

fun recursiveCombat(d1: MutableList<Int>, d2: MutableList<Int>): Pair<Int, MutableList<Int>> {
    val playedRounds = mutableSetOf<Pair<List<Int>, List<Int>>>()
    while (d1.isNotEmpty() && d2.isNotEmpty()) {
        val round = d1.toList() to d2.toList()
        if (playedRounds.contains(round)) return (1 to d1) else playedRounds.add(round)

        val card1 = d1.removeFirst()
        val card2 = d2.removeFirst()

        val winner = if (d1.size >= card1 && d2.size >= card2) {
            recursiveCombat(d1.toList().take(card1).toMutableList(), d2.toList().take(card2).toMutableList()).first
        } else if (card1 > card2) 1
        else 2

        if (winner == 1) d1.addAll(listOf(card1, card2)) else d2.addAll(listOf(card2, card1))
    }

    return if (d1.isNotEmpty()) 1 to d1 else 2 to d2
}
