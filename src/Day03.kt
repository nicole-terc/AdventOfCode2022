fun main() {

    fun Char.getNumValue(): Int =
            if (this.isLowerCase()) {
                this.code - 'a'.code + 1
            } else {
                this.code - 'A'.code + 27
            }

    fun part1(input: List<String>): Int {
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

    fun part2(input: List<String>): Int =
            input.chunked(3).sumOf { group ->
                val(one,two,three) = group
                (one.toSet() intersect two.toSet() intersect three.toSet()).single().getNumValue()
            }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)


    val input = readInput("Day03")
    println("PART 1: " + part1(input))
    println("PART 2: " + part2(input))
}
