import kotlin.math.abs

operator fun P.plus(second: P): P = Pair(this.first + second.first, this.second + second.second)

operator fun Int.times(p: Pair<Int, Int>): Pair<Int, Int> = Pair(this * p.first, this * p.second)

fun P.manhattanDistance() = abs(this.first) + abs(this.second)

fun P.neighbours() = listOf(P(-1, 0), P(1, 0), P(0, -1), P(0, 1)).map { delta -> this + delta }

fun P.neighboursIncludingDiagonals() =
    listOf(P(-1, 0), P(1, 0), P(0, -1), P(0, 1), P(-1, -1), P(1, -1), P(1, 1), P(-1, 1)).map { delta -> this + delta }

fun Char.asLong(): Long = this.toString().toLong()
