import util.readDoubleLineSeparatedFileToGroups

fun main() {
    val groups = readDoubleLineSeparatedFileToGroups("2020.6.txt")

    println("Part one: ${getAllUniqueAnswersByGroup(groups).map { it -> it.size }.sum()}")
    println("Part one: ${getCommonAnswersByGroup(groups).map { it -> it.size }.sum()}")
}

fun getAllUniqueAnswersByGroup (groups: List<String>): List<Set<Char>> {
    return groups.map { group ->
        group.replace("\n", "")
            .toSet()
    }
}

fun getCommonAnswersByGroup (groups: List<String>): List<Set<Char>> {
    return groups.map { group ->
        group.split("\n")
            .map { it -> it.toSet() }
            .reduce { it, next -> it intersect next}
    }
}
