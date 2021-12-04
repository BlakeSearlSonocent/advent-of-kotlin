package aoc.`2021`

import util.readDoubleLineSeparatedFileToGroups

fun main() {
    val input = readDoubleLineSeparatedFileToGroups("2021.4.txt")
    val calls = input[0].split(",").map { it.toInt() }
    println(calls)

    val cards = input.drop(1).map { card ->
        val stringLines = card.split("\n").map { it.trim() }
        BingoCard(stringLines.map { line -> line.split("\\s+".toRegex()).map { it.toInt() to false } })
    }

//    for (call in calls) {
//        if (cards.map { card -> card.updateAndCheck(call) }.any { it }) {
//            println("Done")
//            break
//        }
//    }

    val completedCards = mutableListOf<Int>()
    for (call in calls) {
        val newlyCompleted =
            cards.withIndex().filter { card -> card.index !in completedCards && card.value.updateAndCheck(call) }
                .map { it.index }

        completedCards += newlyCompleted

        if (completedCards.size == cards.size) {
            println(cards[newlyCompleted.first()].sumCard() * call)
            break
        }
    }
}

data class BingoCard(var rows: List<List<Pair<Int, Boolean>>>) {
    fun updateAndCheck(call: Int): Boolean {
        rows = rows.map { row -> row.map { if (it.first == call) Pair(it.first, true) else it } }

        val completedRows = rows.count { row -> row.count { it.second } == 5 }
        val completedColumns = (0..4).count { index -> rows.count { it[index].second } == 5 }

        if (completedRows > 0 || completedColumns > 0) {
            println(sumCard())

            return true
        }

        return false
    }

    fun sumCard() = rows.sumOf { row -> row.sumOf { if (!it.second) it.first else 0 } }
}
