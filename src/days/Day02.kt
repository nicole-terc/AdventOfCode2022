package days

import days.Shape.*
import readInput

enum class Shape(val symbols: List<Char>, val value: Int) {
    ROCK(listOf('A', 'X'), 1),
    PAPER(listOf('B', 'Y'), 2),
    SCISSORS(listOf('C', 'Z'), 3);

    companion object {
        fun parseChar(char: Char): Shape = values().first { it.symbols.contains(char) }
    }
}

fun Shape.winsOver(): Shape = when (this) {
    ROCK -> SCISSORS
    PAPER -> ROCK
    SCISSORS -> PAPER
}

fun Shape.losesOver(): Shape = when (this) {
    ROCK -> PAPER
    PAPER -> SCISSORS
    SCISSORS -> ROCK
}

fun getScore(opponentShape: Shape, myShape: Shape) = when (opponentShape) {
    myShape -> 3
    myShape.winsOver() -> 6
    else -> 0
} + myShape.value

// Part 2 changes:
enum class ExpectedOutcome(val symbol: Char, val value: Int) {
    WIN('Z', 6),
    DRAW('Y', 3),
    LOSE('X', 0);

    companion object {
        fun parseChar(char: Char): ExpectedOutcome = values().first { it.symbol == char }
    }
}

fun getScoreForExpectedOutcome(opponentShape: Shape, expectedOutcome: ExpectedOutcome) = when (expectedOutcome) {
    ExpectedOutcome.WIN -> opponentShape.losesOver().value
    ExpectedOutcome.DRAW -> opponentShape.value
    ExpectedOutcome.LOSE -> opponentShape.winsOver().value
} + expectedOutcome.value


fun main() {

    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach { line ->
            val roundValues = line.split(" ").map { Shape.parseChar(it.first()) }
            score += getScore(roundValues[0], roundValues[1])
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach { line ->
            val roundValues = line.split(" ").map { it.first() }
            score += getScoreForExpectedOutcome(
                Shape.parseChar(roundValues[0]),
                ExpectedOutcome.parseChar(roundValues[1])
            )
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)


    val input = readInput("Day02")
    println("PART 1: " + part1(input))
    println("PART 2: " + part2(input))
}
