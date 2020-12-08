package day8

import console.Console
import util.readFileToLines

fun main() {
    val input = readFileToLines("src/day8/resources/Day8.txt")
    val console = Console(input)

    //Part One
    console.run()
    println("Part one: ${console.acc}")

    //Part two
    for (i in input.indices) {
        val inputCopy = listOf(input).flatten().toMutableList()
        val op = inputCopy[i].split(" ").first()

        if (op == "nop") {
            val replace = inputCopy[i].replace("nop", "jmp")
            inputCopy[i] = replace
            val testConsole = Console(inputCopy)
            if (testConsole.run()) {
                println(testConsole.acc)
            }
        }
        else if (op == "jmp") {
            val replace = inputCopy[i].replace("jmp", "nop")
            inputCopy[i] = replace
            val testConsole = Console(inputCopy)
            if (testConsole.run()) {
                println(testConsole.acc)
            }
        }
    }
}