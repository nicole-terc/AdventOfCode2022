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
open class Day08(
    private val useTestData: Boolean = false
) {
    private var input: List<List<Int>> = emptyList()


    data class MaxHeights(
        var top: Int = 0,
        var bottom: Int = 0,
        var left: Int = 0,
        var right: Int = 0,
    )

    //    @Setup
    fun setup() {
        val stringInput = readInput(if (useTestData) "Day08_test" else "Day08")
        input = stringInput.map { line -> line.toCharArray().map { it.digitToInt() } }
    }

    //    @Benchmark
    fun part1(): Int {
        val visibleTrees: List<MutableList<Boolean>> = List(input.size) { MutableList(input[0].size) { false } }
        val maxHeights: List<MutableList<MaxHeights>> = List(input.size) { MutableList(input[0].size) { MaxHeights() } }

        val rowSize = input[0].size

        for (line in input.indices) {
            for (i in input[0].indices) {
                // TOP left crawl
                val topLeftTreeHeight = input[line][i]

                // top
                val heightTop = if (line == 0) -1 else maxHeights[line - 1][i].top
                if (topLeftTreeHeight > heightTop) {
                    maxHeights[line][i].top = topLeftTreeHeight
                    visibleTrees[line][i] = true
                } else {
                    maxHeights[line][i].top = heightTop
                }

                // left
                val heightLeft = if (i == 0) -1 else maxHeights[line][i - 1].left
                if (topLeftTreeHeight > heightLeft) {
                    maxHeights[line][i].left = topLeftTreeHeight
                    visibleTrees[line][i] = true
                } else {
                    maxHeights[line][i].left = heightLeft
                }

                // BOTTOM right crawl
                val bottomI = rowSize - i - 1
                val bottomLine = input.size - line - 1

                val bottomRightTreeHeight = input[bottomLine][bottomI]

                // bottom
                val heightBottom =
                    if (bottomLine == input.size - 1) -1 else maxHeights[bottomLine + 1][bottomI].bottom
                if (bottomRightTreeHeight > heightBottom) {
                    maxHeights[bottomLine][bottomI].bottom = bottomRightTreeHeight
                    visibleTrees[bottomLine][bottomI] = true
                } else {
                    maxHeights[bottomLine][bottomI].bottom = heightBottom
                }

                // right
                val heightRight =
                    if (bottomI == rowSize - 1) -1 else maxHeights[bottomLine][bottomI + 1].right
                if (bottomRightTreeHeight > heightRight) {
                    maxHeights[bottomLine][bottomI].right = bottomRightTreeHeight
                    visibleTrees[bottomLine][bottomI] = true
                } else {
                    maxHeights[bottomLine][bottomI].right = heightRight
                }
            }
        }

        return visibleTrees.sumOf { it.count { treeVisible -> treeVisible } }
    }

    data class ScenicScore(
        var top: Int = 0,
        var bottom: Int = 0,
        var left: Int = 0,
        var right: Int = 0,
    ) {
        val totalScore: Int
            get() = top * bottom * left * right
    }

    //    @Benchmark
    fun part2(): Int {
        val scores = List(input.size) { MutableList(input[0].size) { ScenicScore() } }

        for (lines in input.indices) {
            for (i in input[0].indices) {
                //top
                var topLine = lines - 1
                while (topLine >= 0 && input[lines][i] > input[topLine][i]) {
                    scores[lines][i].top++
                    topLine--
                }
                if (topLine >= 0) scores[lines][i].top++

                // bottom
                var bottomLine = lines + 1
                while (bottomLine <= input.size - 1 && input[lines][i] > input[bottomLine][i]) {
                    scores[lines][i].bottom++
                    bottomLine++
                }
                if (bottomLine <= input.size - 1) scores[lines][i].bottom++

                // left
                var leftI = i - 1
                while (leftI >= 0 && input[lines][i] > input[lines][leftI]) {
                    scores[lines][i].left++
                    leftI--
                }
                if (leftI >= 0) scores[lines][i].left++

                // right
                var rightI = i + 1
                while (rightI <= input[0].size - 1 && input[lines][i] > input[lines][rightI]) {
                    scores[lines][i].right++
                    rightI++
                }
                if (rightI <= input[0].size - 1) scores[lines][i].right++
            }
        }

        return scores.flatten().maxOf { it.totalScore }
    }
}

fun main() {
    with(Day08(useTestData = true)) {
        setup()
        val part1testSolution = part1()
        val part2testSolution = part2()

        println("TEST 1: $part1testSolution")
        println("TEST 2: $part2testSolution")
        check(part1testSolution == 21)
        check(part2testSolution == 8)
    }

    with(Day08(useTestData = false)) {
        setup()
        println("PART 1: " + part1())
        println("PART 2: " + part2())
    }
}
