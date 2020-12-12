import kotlin.math.abs

operator fun P.plus(second: P): P = Pair(this.first + second.first, this.second + second.second)

operator fun Int.times(p: Pair<Int, Int>): Pair<Int, Int> = Pair(this * p.first, this * p.second)

fun P.manhattanDistance() = abs(this.first) + abs(this.second)