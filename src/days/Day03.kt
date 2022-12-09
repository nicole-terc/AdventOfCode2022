package days

import kotlinx.benchmark.Scope
import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
open class Day03(
    private val useTestData: Boolean = false
) {
    private var input: List<String> = emptyList()

//    @Setup
    fun setup() {
        input = readInput(if (useTestData) "Day03_test" else "Day03")
    }

    fun Char.getNumValue(): Int =
        if (this.isLowerCase()) {
            this.code - 'a'.code + 1
        } else {
            this.code - 'A'.code + 27
        }

//    @Benchmark
    fun part1(): Int {
        var sum = 0
        input.forEach { ruckSack ->
            val compartmentOne = ruckSack.substring(0, ruckSack.length / 2).toCharArray().also { it.sort() }
            val compartmentTwo = ruckSack.substring(ruckSack.length / 2).toCharArray().also { it.sort() }

            var compartmentTwoIndex = 0
            for (item in compartmentOne) {
                while (compartmentTwoIndex < compartmentTwo.size && compartmentTwo[compartmentTwoIndex] < item) {
                    compartmentTwoIndex++
                }

                if (compartmentTwoIndex == compartmentTwo.size) {
                    break
                }

                if (compartmentTwo[compartmentTwoIndex] == item) {
                    sum += item.getNumValue()
                    break
                }
            }
        }
        return sum
    }

//    @Benchmark
    fun part2(): Int =
        input.chunked(3).sumOf { group ->
            val (one, two, three) = group
            (one.toSet() intersect two.toSet() intersect three.toSet()).single().getNumValue()
        }
}


fun main() {
    // test if implementation meets criteria from the description, like:
    with(Day03(useTestData = true)) {
        setup()
        check(part1() == 157)
        check(part2() == 70)
    }
    with(Day03(useTestData = false)) {
        setup()
        println("PART 1: " + part1())
        println("PART 2: " + part2())
    }
}