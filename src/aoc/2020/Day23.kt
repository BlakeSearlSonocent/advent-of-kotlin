import util.readFileToText

fun main() {
    val cups = readFileToText("2020.23.txt").map { Cup(it.toString().toInt(), null) }.toMutableList()
    for ((i, cup) in cups.withIndex()) {
        cup.next = cups[(i + 1) % cups.size]
    }

    var last = cups[8]
    for (i in 10..1_000_000) {
        val next = Cup(i, null)
        cups.add(next)
        last.next = next
        last = next
    }
    last.next = cups[0]

    val labelToCup = mutableMapOf<Int, Cup>()
    val first = cups[0]
    labelToCup[first.label] = first
    var toAdd = first.next!!
    while (toAdd != first) {
        labelToCup[toAdd.label] = toAdd
        toAdd = toAdd.next!!
    }

    play(cups, 10_000_000, labelToCup)
    val oneCup = labelToCup[1]!!
    println(oneCup.next!!.label.toLong() * oneCup.next!!.next!!.label.toLong())
}

private fun play(
    cups: List<Cup>,
    loops: Int,
    labelToCup: MutableMap<Int, Cup>
) {
    var current = cups[0]

    repeat(loops) {
        val a = current.next!!
        val b = a.next!!
        val c = b.next!!

        current.next = c.next

        var destLabel = if (current.label - 1 == 0) cups.size else current.label - 1
        while (destLabel in listOf(a.label, b.label, c.label)) {
            destLabel = if (destLabel == 1) cups.size else destLabel - 1
        }

        val destination = labelToCup[destLabel]!!

        c.next = destination.next
        destination.next = a

        current = current.next!!
    }
}

data class Cup(val label: Int, var next: Cup?)
