import util.readFileToLines

class Passport {
    var creds: Map<String, String>

    constructor(input: List<String>) {
        creds = input
            .flatMap { it.split(" ") }
            .map { it.split(":") }
            .map { it.first() to it[1] }
            .toMap()
    }

    fun validPartOne() = (creds.size == 8) || (creds.size == 7 && !creds.containsKey("cid"))

    fun validPartTwo(): Boolean {
        return validPartOne() &&
            creds["byr"]?.toInt() in 1920..2002 &&
            creds["iyr"]?.toInt() in 2010..2020 &&
            creds["eyr"]?.toInt() in 2020..2030 &&
            matchesRegex(creds["hgt"], Regex("((1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in)")) &&
            matchesRegex(creds["hcl"], Regex("#([0-9]|[a-f]){6}")) &&
            creds["ecl"].let { listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) } &&
            matchesRegex(creds["pid"], Regex("[0-9]{9}"))
    }
}

private fun matchesRegex(value: String?, regex: Regex): Boolean = value?.let { regex.matches(it) } ?: false

fun main() {
    val lines = readFileToLines("2020.4.txt")
    val passports = readLinesToPassports(lines)

    println("Part one: ${passports.count { it.validPartOne() }}")
    println("Part two: ${passports.count { it.validPartTwo() }}")
}

private fun readLinesToPassports(lines: List<String>): MutableList<Passport> {
    val passports = mutableListOf<Passport>()
    val cred = mutableListOf<String>()
    for (line in lines) {
        if (line.isEmpty()) {
            passports.add(Passport(cred))
            cred.clear()
        } else {
            cred.add(line)
        }
    }

    passports.add(Passport(cred))
    return passports
}
