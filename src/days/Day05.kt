package days

import readInput
import java.util.*
import kotlin.math.ceil

fun main() {

    fun getInitialStacks(input: List<String>): List<Stack<Char>> {
        val grid = input.takeWhile { it.contains('[') }
        val numberOfStacks = grid.last().count{it == '['}
        val stacks = List(numberOfStacks) { Stack<Char>() }

        for (i in grid.size.minus(1) downTo 0) {
            grid[i].chunked(4).forEachIndexed { index, chunk ->
                if (chunk[1] != ' ') {
                    stacks[index].push(chunk[1])
                }
            }
        }

        return stacks
    }

    fun part1(input: List<String>): String {
        val stacks = getInitialStacks(input)

        input.filter { it.contains("move") }
            .map { move -> move.split(" ") }
            .map { Triple(it[1].toInt(), it[3].toInt()-1, it[5].toInt()-1) }
            .onEach { moveInfo ->
                for (x in 0 until moveInfo.first) {
                    stacks[moveInfo.third].push(stacks[moveInfo.second].pop())
                }
            }

        var result = ""
        stacks.forEach { stack ->
            if (stack.isNotEmpty()) {
                result += stack.pop()
            }
        }
        return result
    }

    fun part2(input: List<String>): String {
        val stacks = getInitialStacks(input)

        input.filter { it.contains("move") }
            .map { move -> move.split(" ") }
            .map { Triple(it[1].toInt(), it[3].toInt()-1, it[5].toInt()-1) }
            .onEach { moveInfo ->
                val tempStack = Stack<Char>()
                for (x in 0 until moveInfo.first) {
                    tempStack.add(stacks[moveInfo.second].pop())
                }
                while (tempStack.isNotEmpty()) {
                    stacks[moveInfo.third].push(tempStack.pop())
                }
            }

        var result = ""
        stacks.forEach { stack ->
            if (stack.isNotEmpty()) {
                result += stack.pop()
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println("TEST 1: " + part1(testInput))
    println("TEST 2: " + part2(testInput))
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")


    val input = readInput("Day05")
    println("PART 1: " + part1(input))
    println("PART 2: " + part2(input))
}
