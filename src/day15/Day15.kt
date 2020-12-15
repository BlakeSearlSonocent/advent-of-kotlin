package day15

fun main() {
    val input = "6,3,15,13,1,0".split(",").map { it.toInt() }.toMutableList()
    val numberToLastSaid = input.mapIndexed { index, i -> i to index + 1 }.toMap().toMutableMap()

    var lastSaid = input.last()
    val lastSaidTurn = input.dropLast(1).indexOf(lastSaid)
    lastSaid = if (lastSaidTurn == -1) 0 else input.size - lastSaidTurn

    for (thisTurn in (input.size + 1) until 30_000_000) {
        val turnLastSaid = numberToLastSaid[lastSaid]
        numberToLastSaid[lastSaid] = thisTurn
        lastSaid = if (turnLastSaid == null) 0 else thisTurn - turnLastSaid
    }

    print(lastSaid)
}