package aoc.`2021`

import util.readDoubleLineSeparatedFileToGroups

fun main() {
    val input = readDoubleLineSeparatedFileToGroups("2021.4.txt")
    val calls = input[0].split(",").map { it.toInt() }

    val cards = input.drop(1).map { card ->
        val stringLines = card.split("\n").map { it.trim() }
        BingoCard(stringLines.map { line -> line.split("\\s+".toRegex()).map { BingoSquare(it.toInt()) } })
    }

    for (call in calls) {
        val completedCard =
            cards.find { card -> card.updateAndCheckCompletion(call) }?.run { println("Part one: ${sumCard() * call}") }
        if (completedCard != null) break
    }

    cards.forEach { it.reset() }

    val completedCards = mutableListOf<Int>()
    for (call in calls) {
        val newlyCompleted =
            cards.withIndex()
                .filter { card -> card.index !in completedCards && card.value.updateAndCheckCompletion(call) }
                .map { it.index }

        completedCards += newlyCompleted

        if (completedCards.size == cards.size) {
            println("Part two: ${cards[newlyCompleted.first()].sumCard() * call}")
            break
        }
    }
}

data class BingoCard(val rows: List<List<BingoSquare>>) {
    fun updateAndCheckCompletion(call: Int): Boolean {
        rows.forEach { row -> row.forEach { it.completeIfMatch(call) } }

        val completedRows = rows.count { row -> row.count { it.checked } == 5 }
        val completedColumns = (0..4).count { index -> rows.count { it[index].checked } == 5 }

        return completedRows + completedColumns > 0
    }

    fun sumCard() = rows.sumOf { row -> row.filterNot { it.checked }.sumOf { it.number } }
    fun reset() = rows.forEach { it.forEach { square -> square.checked = false } }
}

data class BingoSquare(val number: Int, var checked: Boolean = false) {
    fun completeIfMatch(call: Int) {
        if (number == call) checked = true
    }
}
