package aoc.`2020`

import util.readFileToLines
import java.awt.Desktop
import java.net.URL
import java.net.URLEncoder

fun main() {
    val input = readFileToLines("2020.13.txt")
    val arrival = input.first().toInt()
    val let = input[1].split(",")
        .filter { it != "x" }
        .map { it.toInt() }
        .map { it to it - (arrival % it) }
        .minByOrNull { it.second }
        .let { it!!.first * it.second }

    // Part two is a cheat, need to think of a better way to do it.
    println(
        input[1].split(",").withIndex().filter { it.value != "x" }
            .joinToString(" && ") { (i, v) -> "(x+$i) mod $v = 0" }.let { URLEncoder.encode(it) }
            .let { eq -> Desktop.getDesktop().browse(URL("https://www.wolframalpha.com/input/?i=$eq").toURI()) }
    )
}
