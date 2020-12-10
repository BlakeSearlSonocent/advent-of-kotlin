package day9

import util.readFileToInts
import util.readFileToLongs

val INPUT = readFileToLongs("src/day9/resources/Day9.txt")

fun main() {
    val partOne = partOne(25, 25)
    val partTwo = partTwo(partOne)

    println(partOne)
    println(partTwo)
}

fun partOne(preambleSize: Int, head: Int): Long {
    val preamble = INPUT.subList(head - preambleSize, head)

    val testVal = INPUT[head]
    var foundMatch = false


    for ((ix, x) in preamble.withIndex()) for ((iy, y) in preamble.withIndex()) if (x + y == testVal && ix != iy) foundMatch = true

    return if (!foundMatch) {
        testVal
    }
    else {
        partOne(preambleSize, head+1)
    }
}

fun partTwo(targetVal: Long): Long {
    for (ix in INPUT.indices) {
        for (iy in ix+1 until INPUT.size) {
            val subList = INPUT.subList(ix, iy)
            if (subList.sum() == targetVal) {
                return subList.min()!! + subList.max()!!
            }
        }
    }

    return -1
}