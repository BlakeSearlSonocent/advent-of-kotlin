package aoc.`2020`

fun main() {
    var a = 1L
    var b = 1L

    while (true) {
        a = (a * 7) % 20201227L
        b = (b * 18356117) % 20201227L

        if (a == 5909654L) {
            return println(b)
        }
    }
}
