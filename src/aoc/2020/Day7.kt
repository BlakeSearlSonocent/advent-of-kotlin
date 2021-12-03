import util.readLines

private val REGEX_LINES = """(\w+ \w+) bags contain (.*)""".toRegex()
private val REGEX_BAG = """(\d+) (\w+ \w+) bags?""".toRegex()

data class BagWithCount(val bag: String, val count: Int)

fun parse(lines: List<String>): Map<String, List<BagWithCount>> {
    return lines.associate { l ->
        val (bag, containedBags) = REGEX_LINES.matchEntire(l)!!.destructured
        bag to REGEX_BAG.findAll(containedBags).map { containedBagAndCount ->
            val (count, colour) = containedBagAndCount.destructured
            BagWithCount(colour, count.toInt())
        }.toList()
    }
}

fun getContainingBags(map: Map<String, List<BagWithCount>>, bag: String): Set<String> {
    val containers = map.entries.filter { it.value.map { bagAndCount -> bagAndCount.bag }.contains(bag) }.map { it.key }.toSet()
    return containers.union(containers.flatMap { getContainingBags(map, it) }.toSet())
}

fun getContainingBags(map: Map<String, List<BagWithCount>>, containingBags: MutableList<String>, startBag: String, thisBag: String = startBag): Set<String> {
    val containedBagsAndCounts = map[thisBag] ?: error("These shouldn't be null!")

    containedBagsAndCounts.map { it.bag }
        .forEach { bag ->
            if (bag == "shiny gold") containingBags.add(startBag)
            else getContainingBags(map, containingBags, startBag, bag)
        }

    return containingBags.toSet()
}

fun getContainedBagCount(map: Map<String, List<BagWithCount>>, thisBag: String): Int {
    val containedBagsAndCounts = map[thisBag] ?: error("These shouldn't be null!")
    return containedBagsAndCounts.sumBy { it.count * (1 + getContainedBagCount(map, it.bag)) }
}

fun main() {
    val input = readLines("2020.7.txt")
    val bagToContents = parse(input)

    println("Part one: ${getContainingBags(bagToContents, "shiny gold").size}")
    println("Part two: ${getContainedBagCount(bagToContents, "shiny gold")}")
}
