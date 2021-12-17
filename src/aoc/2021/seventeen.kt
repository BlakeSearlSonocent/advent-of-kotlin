import kotlin.math.abs
import kotlin.math.max

const val xMin = 60
const val xMax = 94
const val yMin = -171
const val yMax = -136

class Dart(var velocity: P, var position: P = P(0, 0)) {
    fun reachesTarget(): Boolean {
        while (!fails()) {
            position += velocity

            if (position.first in xMin..xMax && position.second in yMin..yMax) {
                return true
            }

            velocity = P(max(0, velocity.first - 1), velocity.second - 1)
        }

        return false
    }

    private fun fails() = position.first > xMax || position.second < yMin
}

fun main() {
    println(
        (1..xMax).flatMap { vX ->
            (yMin until abs(yMin)).map { vY ->
                Dart(P(vX, vY))
            }
        }.filter { it.reachesTarget() }.count()
    )
}
