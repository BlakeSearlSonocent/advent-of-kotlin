package aoc.`2020`

import java.io.File

val REGEX_RULE = """([a-z ]+): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()

fun main() {
    val input = File("20201.16.txt").readText().split("\n\n")

    val rules = input.first().split("\n").map {
        val (name, a1, b1, a2, b2) = REGEX_RULE.find(it)!!.destructured
        Rule(name, a1.toInt()..b1.toInt(), a2.toInt()..b2.toInt())
    }

    val myTicket = input[1].split("\n")[1].split(",").map { it.toInt() }
    val otherTickets = input[2].split("\n").drop(1).map { it.split(",").map { field -> field.toInt() } }

    println("Part one: ${partOne(rules, otherTickets)}")
    println("Part two: ${partTwo(rules, myTicket, otherTickets)}")
}

fun partTwo(rules: List<Rule>, myTicket: List<Int>, otherTickets: List<List<Int>>): Long {
    val validTickets = getValidTickets(otherTickets, rules)
    val ruleToPossibleFields = getPossibleRulesForFields(rules, myTicket, validTickets)
    val reduceRuleToFields = reduceRuleToFields(ruleToPossibleFields)

    val departureIndices = reduceRuleToFields.filter { it.first.name.startsWith("departure") }.map { it.second.first() }
    return myTicket.map { it.toLong() }.filterIndexed { index, _ -> departureIndices.contains(index) }.reduce { acc, i -> acc * i }
}

private fun getPossibleRulesForFields(
    rules: List<Rule>,
    myTicket: List<Int>,
    validTickets: List<List<Int>>
): MutableList<Pair<Rule, MutableList<Int>>> = rules.map { rule ->
    rule to myTicket.indices.filter { column ->
        validTickets.map { it[column] }.all { it in rule.range1 || it in rule.range2 }
    }.toMutableList()
}.toMutableList()

private fun getValidTickets(
    otherTickets: List<List<Int>>,
    rules: List<Rule>
): List<List<Int>> = otherTickets.filter { ticket ->
    ticket.all { field ->
        rules.any { rule ->
            field in rule.range1 || field in rule.range2
        }
    }
}

fun reduceRuleToFields(
    ruleToPossibleFields: MutableList<Pair<Rule, MutableList<Int>>>,
    reducedRuleToField: MutableList<Pair<Rule, List<Int>>> = mutableListOf()
): MutableList<Pair<Rule, List<Int>>> {
    val unique = ruleToPossibleFields.filter { it.second.size == 1 }

    if (unique.isEmpty()) return reducedRuleToField

    val uniqueCols = unique.map { it.second }.flatten()
    ruleToPossibleFields.removeAll(unique)
    ruleToPossibleFields.forEach { it.second.removeAll(uniqueCols) }
    reducedRuleToField.addAll(unique)

    return reduceRuleToFields(ruleToPossibleFields, reducedRuleToField)
}

fun partOne(
    rules: List<Rule>,
    otherTickets: List<List<Int>>
): Int = otherTickets.flatten()
    .filter { field ->
        !rules.any { rule ->
            field in rule.range1 || field in rule.range2
        }
    }.sum()

data class Rule(val name: String, val range1: IntRange, val range2: IntRange)
