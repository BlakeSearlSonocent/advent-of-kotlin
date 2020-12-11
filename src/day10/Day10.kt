package day10

import util.readFileToLongs

fun main() {
    val input = readFileToLongs("src/day10/resources/Day10.txt")
    input.addAll(listOf(0, input.max()!! + 3))
    input.sort()

    println("Part one: ${partOne(input)}")
    println("Part two: ${partTwo(input)}")
}

fun partOne(input: List<Long>): Int {
    val groupings = input.zipWithNext()
        .map { it.second - it.first }
        .groupingBy { it }
        .eachCount()

    return groupings[3]!! * groupings[1]!!
}

fun partTwo(input: MutableList<Long>) = noWaysFrom(0, input, mutableMapOf())

fun noWaysFrom(i: Int, input: List<Long>, cachedNoWaysFrom: MutableMap<Int, Long>): Long {
    if (i == input.size - 1) return 1
    return cachedNoWaysFrom.getOrElse(i) {
        var ans = 0L
        for (j in i+1..minOf(i+3, input.size-1)) {
            if ((input[j] - input[i]) <= 3) ans+= noWaysFrom(j, input, cachedNoWaysFrom)
        }

        cachedNoWaysFrom[i] = ans
        return ans
    }
}