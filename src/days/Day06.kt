package days

import readInput

fun main() {

    fun findSequenceForPacket(input: String, windowSize: Int): Int =
        input.windowedSequence(windowSize).indexOfFirst { window ->
            window.toSet().size == windowSize
        } + windowSize


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println("TEST 1: " + findSequenceForPacket(testInput.first(), 4))
    println("TEST 2: " + findSequenceForPacket(testInput.first(), 14))
    check(findSequenceForPacket(testInput.first(), 4) == 11)
    check(findSequenceForPacket(testInput.first(), 14) == 26)


    val input = readInput("Day06")
    println("PART 1: " + findSequenceForPacket(input.first(), 4))
    println("PART 2: " + findSequenceForPacket(input.first(), 14))
}
