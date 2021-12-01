import util.readFileToLines

val REGEX_MEM = """[0-9]+""".toRegex()
val MEM = mutableMapOf<Long, Long>()

fun main() {
    val lines = readFileToLines("2020.14.txt")
    var (andMask, orMask) = listOf(0L, 0L)
    for (s in lines) {
        if (s.take(7) == "mask = ") {
            val maskStr = s.drop(7)
            andMask = maskStr.replace('X', '1').toLong(2)
            orMask = maskStr.replace('X', '0').toLong(2)
        }
        if (s.take(4) == "mem[") {
            val (mem, value) = REGEX_MEM.findAll(s).map { it.value.toLong() }.toList()
            MEM[mem] = (value and andMask) or orMask
        }
    }

    println(MEM.map { it.value }.sum())
}
