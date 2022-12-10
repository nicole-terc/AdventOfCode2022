package days

import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
open class Day10(
    private val useTestData: Boolean = false
) {
    private var input: List<String> = emptyList()

    @Setup
    fun setup() {
        input = readInput(if (useTestData) "Day10_test" else "Day10")
    }


    // Part 1 - Option 1
    private fun Int.isSignalStrengthCycle() = this % 40 == 20

    @Benchmark
    fun part1(): Int {
        var x = 1
        var currentCycle = 1
        var signalStrength = 0

        input.forEach { instruction ->
            currentCycle++

            if (currentCycle.isSignalStrengthCycle()) {
                signalStrength += (currentCycle * x)
            }

            if (instruction != "noop") {
                currentCycle++
                x += instruction.substringAfter(" ").toInt()

                if (currentCycle.isSignalStrengthCycle()) {
                    signalStrength += (currentCycle * x)
                }
            }
        }

        return signalStrength
    }

    // Part 1 - Option 2
    @Benchmark
    fun part1b(): Int {
        var x = 1
        var currentCycle = 1
        var signalStrength = 0

        fun increaseSignalStrength() {
            if (currentCycle % 40 == 20) {
                signalStrength += (currentCycle * x)
            }
        }

        input.forEach { instruction ->
            currentCycle++
            increaseSignalStrength()

            if (instruction != "noop") {
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
                increaseSignalStrength()
            }
        }

        return signalStrength
    }

    @Benchmark
    fun part1cSequence(): Int {
        var x = 1
        var currentCycle = 1
        val xSequence = mutableListOf<Int>()

        input.forEach { instruction ->
            xSequence.add(x)
            currentCycle++

            if (instruction != "noop") {
                xSequence.add(x)
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
            }
        }

        return setOf(20, 60, 100, 140, 180, 220).sumOf { xSequence[it - 1] * it }
    }

    // Part 2 - Option 1
    private fun printCRTValue(index: Int, x: Int) {
        val adjustedIndex = index % 40

        if (adjustedIndex == 0) {
            println()
        }

        if ((adjustedIndex) in x - 1..x + 1) {
            print("#")
        } else {
            print(".")
        }
    }

    @Benchmark
    fun part2() {
        var x = 1
        var currentCycle = 1

        input.forEach { instruction ->
            printCRTValue(currentCycle - 1, x)
            currentCycle++

            if (instruction != "noop") {
                printCRTValue(currentCycle - 1, x)
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
            }
        }
    }

    // Part 2 - Option 2
    @Benchmark
    fun part2bArrays(): Array<CharArray> {
        var x = 1
        var currentCycle = 1
        val screen = Array(6) { CharArray(40) { '.' } }

        fun drawPixel() {
            val horizontalPos = (currentCycle - 1) % 40
            if (horizontalPos in x - 1..x + 1) {
                screen[(currentCycle - 1) / 40][horizontalPos] = '#'
            }
        }

        input.forEach { instruction ->
            drawPixel()
            currentCycle++

            if (instruction != "noop") {
                drawPixel()
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
            }
        }
        return screen
    }

    // Part 2 - Option 3
    @Benchmark
    fun part2bLists(): List<List<Char>> {
        var x = 1
        var currentCycle = 1
        val screen = List(6) { MutableList(40) { '.' } }

        fun drawPixel() {
            val horizontalPos = (currentCycle - 1) % 40
            if (horizontalPos in x - 1..x + 1) {
                screen[(currentCycle - 1) / 40][horizontalPos] = '#'
            }
        }

        input.forEach { instruction ->
            drawPixel()
            currentCycle++

            if (instruction != "noop") {
                drawPixel()
                currentCycle++
                x += instruction.substringAfter(" ").toInt()
            }
        }
        return screen
    }

}

fun main() {
    with(Day10(useTestData = true)) {
        setup()
        check(part1() == 13140)
        check(part1b() == 13140)
        check(part1cSequence() == 13140)
        val testPart2ResultArray =
            """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent().lines().map { it.toCharArray() }.toTypedArray()

        check(part2bArrays().contentDeepEquals(testPart2ResultArray))
        check(part2bLists() == testPart2ResultArray.toList().map { it.toList() })
    }

    with(Day10(useTestData = false)) {
        setup()
        println("PART 1: " + part1b()) //15880
        println("PART 2: ") // PLGFKAZG
        part2bArrays().forEach { println(it) }
    }
}
