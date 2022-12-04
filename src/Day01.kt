fun main() {
    fun createElvesSnacksList(input: List<String>): List<List<Int>>{
        val elvesSnacks: MutableList<MutableList<Int>> = mutableListOf(mutableListOf())

        var index = 0
        input.forEach {
            if(it.isNotBlank()){
                elvesSnacks[index].add(it.toInt())
            } else {
                index++
                elvesSnacks.add(mutableListOf())
            }
        }
        return elvesSnacks
    }

    fun part1(input: List<String>): Int {
        val elvesSnacks = createElvesSnacksList(input)
        return elvesSnacks.maxOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        val elvesSnacks = createElvesSnacksList(input)
        return elvesSnacks.map { it.sum() }.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)


    val input = readInput("Day01")
    println("PART 1: " + part1(input))
    println("PART 2: " + part2(input))
}
