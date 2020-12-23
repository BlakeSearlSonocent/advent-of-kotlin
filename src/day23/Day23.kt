package day23

import util.readFileToText

fun main() {
    val cups = readFileToText("resources/Day23.txt").map { Cup(it.toString().toInt(), null) }
    for ((i, cup) in cups.withIndex()) {
        cup.next = cups[(i + 1) % cups.size]
    }

    var current = cups[0]

    repeat(100) {
        println("1")
        val next = current.next!!
        current.next = current.next!!.next!!.next!!.next

        var destLabel = if (current.label - 1 == 0) 9 else current.label - 1
        while (destLabel in listOf(next.label, next.next!!.label, next.next!!.next!!.label)) {
            destLabel = if (destLabel == 1) 9 else destLabel - 1
        }

        println(2)

        println("Destination label: $destLabel")

        var destination = next
        while (destination.label != destLabel) destination = destination.next!!

        println(3)

        next.next!!.next!!.next = destination.next
        destination.next = next

        current = current.next!!
    }

    var cup = cups[0]
    while (cup.label != 1) {
        cup = cup.next!!
    }
    while (cup.next!!.label != 1) {
        print(cup.next!!.label)
        cup = cup.next!!
    }
}



data class Cup(val label: Int, var next: Cup?)