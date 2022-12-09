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
open class DayBenchmarkTemplate(
    private val useTestData: Boolean = false
) {
    private var input: List<String> = emptyList()

    //    @Setup
    fun setup() {
        input = readInput(if (useTestData) "Day01_test" else "Day01")
    }

    //    @Benchmark
    fun part1(): Int {
        return input.size
    }

    //    @Benchmark
    fun part2(): Int {
        return input.size
    }
}

fun main() {
    with(Day01(useTestData = true)) {
        setup()
        val part1testSolution = part1()
        val part2testSolution = part2()

        println("TEST 1: $part1testSolution")
        println("TEST 2: $part2testSolution")
        check(part1testSolution == 1)
        check(part2testSolution == 1)
    }

    with(Day01(useTestData = false)) {
        setup()
        println("PART 1: " + part1())
        println("PART 2: " + part2())
    }
}
