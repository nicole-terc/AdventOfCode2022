package days

import readInput

fun IntRange.contains(range: IntRange): Boolean {
    return this.contains(range.first) && this.contains(range.last)
}

fun IntRange.overlap(range: IntRange): Boolean {
    return this.contains(range.first) || this.contains(range.last)
}

fun main() {

    fun part1(input: List<String>) = input
        .map { line ->
            line.split(",").map { range -> range.substringBefore("-").toInt()..range.substringAfter("-").toInt() }
        }
        .filter { pairs -> pairs[0].contains(pairs[1]) || pairs[1].contains(pairs[0]) }
        .size

    fun part2(input: List<String>) = input
        .map { line ->
            line.split(",").map { range -> range.substringBefore("-").toInt()..range.substringAfter("-").toInt() }
        }
        .filter { pairs -> pairs[0].overlap(pairs[1]) || pairs[1].overlap(pairs[0]) }
        .size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)


    val input = readInput("Day04")
    println("PART 1: " + part1(input))
    println("PART 2: " + part2(input))
}
