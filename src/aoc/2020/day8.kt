import console.Console
import util.readFileToLines

fun main() {
    val input = readFileToLines("2020.8.txt")
    val console = Console(input)

    // Part One
    console.run()
    println("Part one: ${console.acc}")

    // Part two
    console.resetState()
    print("Part two: ")
    console.runCorruptionFix()
}
