package days

import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
open class Day01(
    private val useTestData: Boolean = false
) {
    private var input: List<String> = emptyList()

    @Setup
    fun setup() {
        input = readInput(if (useTestData) "Day01_test" else "Day01")
    }

    fun createElvesSnacksList(input: List<String>): List<List<Int>> {
        val elvesSnacks: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())

        var index = 0
        input.forEach {
            if (it.isNotBlank()) {
                elvesSnacks[index].add(it.toInt())
            } else {
                index++
                elvesSnacks.add(mutableListOf())
            }
        }
        return elvesSnacks
    }

    @Benchmark
    fun part1(): Int {
        val elvesSnacks = createElvesSnacksList(input)
        return elvesSnacks.maxOf { it.sum() }
    }

    @Benchmark
    fun part2(): Int {
        val elvesSnacks = createElvesSnacksList(input)
        return elvesSnacks.map { it.sum() }.sortedDescending().take(3).sum()
    }
}

fun main() {
    // test if implementation meets criteria from the description, like:
    with(Day01(useTestData = true)) {
        setup()
        check(part1() == 24000)
        check(part2() == 45000)
    }
    with(Day01(useTestData = false)) {
        setup()
        println("PART 1: " + part1())
        println("PART 2: " + part2())
    }
}