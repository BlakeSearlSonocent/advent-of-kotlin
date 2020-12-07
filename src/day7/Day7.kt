package day7

import util.readFileToLines

private val REGEX_LINES = """(\w+ \w+) bags contain (.*)""".toRegex()
private val REGEX_BAG = """(\d+) (\w+ \w+) bags?""".toRegex()

fun parse(lines: List<String>): Map<String, List<Pair<String, Int>>> {
    return lines.associate { l ->
        val (bag, containedBags) = REGEX_LINES.matchEntire(l)!!.destructured
        bag to REGEX_BAG.findAll(containedBags).map { containedBagAndCount ->
            val (count, colour) = containedBagAndCount.destructured
            colour to count.toInt()
        }.toList()
    }
}

fun getContainingBags (map: Map<String, List<Pair<String, Int>>>, containingBags: MutableList<String>, startBag: String, thisBag: String = startBag): Set<String> {
    val containedBagsAndCounts = map[thisBag] ?: error("These shouldn't be null!")

    containedBagsAndCounts.map { it.first }
        .forEach { bag ->
            if (bag == "shiny gold") containingBags.add(startBag)
            else getContainingBags(map, containingBags, startBag, bag)
        }

    return containingBags.toSet()
}

fun getContainedBag (map: Map<String, List<Pair<String, Int>>>, thisBag: String): Int {
    val containedBagsAndCounts = map[thisBag] ?: error("These shouldn't be null!")

    return containedBagsAndCounts.sumBy { it.second * (1 + getContainedBag(map, it.first)) }
}


fun main() {
    val input = readFileToLines("src/day7/resources/Day7.txt")
    val parsed = parse(input)
    println(parsed.map { getContainingBags(parsed, mutableListOf(), it.key) }
        .flatten()
        .size)

    println(getContainedBag(parsed, "shiny gold"))
}


