package day15

fun main() {
    val input = "6,3,15,13,1,0".split(",").map { it.toInt() }.toMutableList()
    val noToTurnLastSaid = input.mapIndexed { index, i -> i to index + 1 }.toMap().toMutableMap()

    var lastSaid = input.last()

    for (thisTurn in input.size until 30_000_000) {
        val turnLastSaid = noToTurnLastSaid[lastSaid]
        noToTurnLastSaid[lastSaid] = thisTurn
        lastSaid = if (turnLastSaid == null) 0 else thisTurn - turnLastSaid
    }

    print(lastSaid)
}