package days

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println("TEST 1: " + part1(testInput))
    println("TEST 2: " + part2(testInput))
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)


    val input = readInput("Day01")
    println("PART 1: " + part1(input))
    println("PART 2: " + part2(input))
}
