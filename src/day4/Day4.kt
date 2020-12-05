package day4

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

    fun partOne() = (creds.size == 8) || (creds.size == 7 && !creds.containsKey("cid"))
    fun partTwo(): Boolean {
        return partOne()
                && creds["byr"]?.toInt() in 1920..2002
                && creds["iyr"]?.toInt() in 2010..2020
                && creds["eyr"]?.toInt() in 2020..2030
                && creds["hgt"]?.let { Regex("((1[5-8][0-9]|19[0-3])cm|(59|6[0-9]|7[0-6])in)").matches(it) }!!
                && creds["hcl"]?.let { Regex("#([0-9]|[a-f]){6}").matches(it) }!!
                && creds["ecl"].let { listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) }
                && creds["pid"].let { Regex("[0-9]{9}").matches(it!!) }
    }
}

fun main() {
    var elMapo = mapOf("a" to "b")
    val truthy = elMapo["iyr"]?.let { Regex("abc").matches(it) }!!
    print(truthy && true)


//    val lines = readFileToLines("src/day4/resources/Day4.txt")
//
//    val passports = mutableListOf<Passport>()
//    val cred = mutableListOf<String>()
//    for (line in lines) {
//        if (line.isEmpty()) {
//            passports.add(Passport(cred))
//            cred.clear()
//        } else {
//            cred.add(line)
//        }
//    }
//
//    passports.add(Passport(cred))
//
//    println("Part one: ${passports.count { it.partOne() }}")
//    println("Part two: ${passports.count { it.partTwo() }}")
}