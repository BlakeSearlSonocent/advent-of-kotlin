package aoc.`2020`

import util.readLines

fun main() {
    val ingredientsToAllergens = parseInput(readLines("2020.21.txt"))

    val allergenToPotentialIngredient = mutableMapOf<String, Set<String>>()
    val ingredientToCount = mutableMapOf<String, Int>()
    ingredientsToAllergens.forEach { entry ->
        for (allergen in entry.value) {
            val ingredients = allergenToPotentialIngredient.getOrDefault(allergen, emptySet())
            allergenToPotentialIngredient[allergen] = if (ingredients.isEmpty()) entry.key else ingredients intersect entry.key
        }

        for (ingredient in entry.key) {
            ingredientToCount[ingredient] = ingredientToCount.getOrDefault(ingredient, 0) + 1
        }
    }

    for (ingredient in allergenToPotentialIngredient.values.flatten().toSet()) {
        ingredientToCount.remove(ingredient)
    }

    println("Part one: ${ingredientToCount.values.sum()}")
    println("Part two: ${partTwo(allergenToPotentialIngredient)}")
}

private fun partTwo(allergenToPotentialIngredients: MutableMap<String, Set<String>>) =
    reduceAllergenToIngredient(allergenToPotentialIngredients)
        .toSortedMap().values.joinToString(",")

fun reduceAllergenToIngredient(allergenToPotentialIngredients: MutableMap<String, Set<String>>): MutableMap<String, String> {
    val ingredientsByAllergen = allergenToPotentialIngredients.map { it.key to it.value.toMutableSet() }.toMap().toMutableMap()
    val matches = mutableMapOf<String, String>()

    while (ingredientsByAllergen.isNotEmpty()) {
        val newMatches = ingredientsByAllergen.filter { it.value.size == 1 }.map { it.key to it.value.first() }.toMap()
        matches.putAll(newMatches)

        newMatches.keys.forEach { ingredientsByAllergen.remove(it) }
        ingredientsByAllergen.values.forEach { it.removeAll(newMatches.values) }
    }

    return matches
}

fun parseInput(input: List<String>) =
    input.map { line ->
        val ingredients = line.substringBefore(" (").split(" ").toSet()
        val allergens = line.substringAfter("(contains ").substringBefore(")").split(", ").toSet()
        ingredients to allergens
    }.toMap()
