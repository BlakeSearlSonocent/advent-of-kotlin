package day19

import util.readFileToText

fun main() {
    val input = readFileToText("resources/Day19.txt").split("\n\n")
    val rules = input.first().split("\n")
        .map { it.split(":").let { (ruleNo, rule) -> ruleNo.toInt() to rule.trim() } }
        .toMap()

    val rulesPartTwo = rules + listOf(8 to "42 | 42 8", 11 to "42 31 | 42 11 31").toMap()

    var messages = input[1].split("\n")

    println("Part one: ${messages.count { doesMatch(rules, it, listOf(0)) }}")
    println("Part one: ${messages.count { doesMatch(rulesPartTwo, it, listOf(0)) }}")
}

fun doesMatch(ruleMap: Map<Int, String>, line: CharSequence, rules: List<Int>): Boolean {
    if (line.isEmpty()) {
        return rules.isEmpty()
    } else if (rules.isEmpty()) {
        return false
    }

    return ruleMap.getValue(rules[0]).let { rule ->
        if (rule[1] in 'a'..'z') {
            if (line.startsWith(rule[1])) {
                doesMatch(ruleMap, line.drop(1), rules.drop(1))
            } else false
        } else {
            rule.split(" | ").any { doesMatch(ruleMap, line, it.split(" ").map(String::toInt) + rules.drop(1)) }
        }
    }
}
