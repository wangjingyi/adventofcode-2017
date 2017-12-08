package day6

private fun reallocate(l: MutableList<Int>) : List<Int> {
    val size = l.size
    val (i, v) = l.withIndex().maxBy {it.value}!!
    l[i] = 0
    val n = v / size
    val reminder = v % size
    (i + 1..i + reminder).forEach {l[it % size] += n + 1} // from next index of max
    (i + reminder + 1..size - 1).forEach{l[it % size] += n }
    return l.toList()
}

fun solve1() : Int {

    val start = listOf(2, 8, 8, 5, 4, 2 ,3, 1, 5, 5, 1, 2, 15, 13, 5, 14)

    tailrec fun distribute(l: MutableList<Int>, seen: MutableSet<List<Int>> = mutableSetOf(start), steps: Int = 0): Int {

        val snapshot = reallocate(l)
        if(seen.contains(snapshot))
            return steps + 1
        else {
            seen.add(snapshot)
            return distribute(l, seen,steps + 1)
        }
    }

    return distribute(start.toMutableList())
}

fun solve2() : Int {

    val start = listOf(2, 8, 8, 5, 4, 2 ,3, 1, 5, 5, 1, 2, 15, 13, 5, 14)

    tailrec fun distribute(l: MutableList<Int>, seen: MutableMap<List<Int>, Int> = mutableMapOf(start to 0), steps: Int = 0): Int {

        val snapshot = reallocate(l)
        if(seen.contains(snapshot))
            return steps + 1 - seen[snapshot]!!
        else {
            seen[snapshot] = steps + 1
            return distribute(l, seen,steps + 1)
        }
    }

    return distribute(start.toMutableList())
}

