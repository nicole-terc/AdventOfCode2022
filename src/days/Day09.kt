package days

import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import org.openjdk.jmh.annotations.*
import readInput
import java.lang.Integer.max
import java.util.concurrent.TimeUnit
import kotlin.math.abs

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
open class Day09(
    val fileName: String = "Day09"
) {
    private var input: List<Pair<String, Int>> = emptyList()

    data class Position(
        val x: Int = 0,
        val y: Int = 0,
    )

    fun Position.plus(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }

    fun Position.minusCoerced(other: Position): Position {
        return Position((x - other.x).coerceIn(-1, 1), (y - other.y).coerceIn(-1, 1))
    }

    private fun adjustedTailPosition(headPosition: Position, tailPosition: Position): Position {
        val distance = max(abs(headPosition.x - tailPosition.x), abs(headPosition.y - tailPosition.y))
        val difference = headPosition.minusCoerced(tailPosition)
        return if (distance <= 1) {
            tailPosition
        } else {
            tailPosition.plus(difference)
        }
    }

    @Setup
    fun setup() {
        input = readInput(fileName).map { line ->
            line.split(" ").let { list ->
                list.first() to list.last().toInt()
            }
        }
    }

    @Benchmark
    fun part1(): Int {
        var headPosition = Position()
        var tailPosition = Position()
        val tailPositionsVisited = mutableSetOf<Position>()

        for ((direction, times) in input) {
            val positionDiff = when (direction) {
                "U" -> Position(0, 1)
                "D" -> Position(0, -1)
                "R" -> Position(1, 0)
                "L" -> Position(-1, 0)
                else -> throw IllegalArgumentException("Unknown direction: $direction")
            }

            repeat(times) {
                headPosition = headPosition.plus(positionDiff)
                tailPosition = adjustedTailPosition(headPosition, tailPosition)
                tailPositionsVisited.add(tailPosition)
            }
        }
        return tailPositionsVisited.size
    }

    @Benchmark
    fun part2(): Int {
        val ropePositions = MutableList(10) { Position() }
        val tailPositionsVisited = mutableSetOf<Position>()

        input.asSequence().forEach { (direction, times) ->

            val positionDiff = when (direction) {
                "U" -> Position(0, 1)
                "D" -> Position(0, -1)
                "R" -> Position(1, 0)
                "L" -> Position(-1, 0)
                else -> throw IllegalArgumentException("Unknown direction: $direction")
            }

            repeat(times) {
                ropePositions[0] = ropePositions[0].plus(positionDiff)
                for (i in 1 until ropePositions.size) {
                    ropePositions[i] = adjustedTailPosition(ropePositions[i - 1], ropePositions[i])
                }
                tailPositionsVisited.add(ropePositions.last())
            }
        }
        return tailPositionsVisited.size
    }

    /// option 2
    private val directionsMap = mapOf(
        "U" to Position(0, 1),
        "D" to Position(0, -1),
        "R" to Position(1, 0),
        "L" to Position(-1, 0),
    )

    @Benchmark
    fun part2b(): Int {
        val ropePositions = MutableList(10) { Position() }
        val tailPositionsVisited = mutableSetOf<Position>()

        input.asSequence().forEach { (direction, times) ->

            val positionDiff = directionsMap[direction] ?: Position()

            repeat(times) {
                ropePositions[0] = ropePositions[0].plus(positionDiff)
                for (i in 1 until ropePositions.size) {
                    ropePositions[i] = adjustedTailPosition(ropePositions[i - 1], ropePositions[i])
                }
                tailPositionsVisited.add(ropePositions.last())
            }
        }
        return tailPositionsVisited.size
    }

}

fun main() {
    with(Day09(fileName = "Day09_test")) {
        setup()
        val part1testSolution = part1()
        val part2testSolution = part2()

        println("TEST 1: $part1testSolution")
        println("TEST 2: $part2testSolution")
        check(part1testSolution == 13)
        check(part2testSolution == 1)
    }

    with(Day09(fileName = "Day09_test02")) {
        setup()
        val part2testSolution = part2()

        println("TEST 2 #2: $part2testSolution")
        check(part2testSolution == 36)
    }

    with(Day09(fileName = "Day09")) {
        setup()
        println("PART 1: " + part1())
        println("PART 2: " + part2())
        println("PART 2b: " + part2b())
    }
}
