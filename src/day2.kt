package day2

fun solve1(): Int {
    val ls = util.readLines().asSequence().map {"""\s+""".toRegex().split(it).map {it.toInt()}}
    return ls.map {it.max()!!.toInt() - it.min()!!.toInt()!!}.sum()
}

private fun List<Int>.divisible(): Int {
    (0..this.size - 2).forEach { i ->
        (i + 1 until this.size).forEach { j ->
            val a = this[i]
            val b = this[j]
            when {
                a % b == 0 -> return a / b
                b % a == 0 -> return b / a
            }
        }
    }
    return 0
}

fun solve2(): Int {
    val ls = util.readLines().asSequence().map {"""\s+""".toRegex().split(it).map {it.toInt()}}
    return ls.asSequence().map(List<Int>::divisible).sum()
}

fun day2() {
    println(day2.solve1())
    println(day2.solve2())
}