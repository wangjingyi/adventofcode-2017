package day16

fun solve1(): String = dance(START.toMutableMap()).backToString()

fun solve2() = (0 until 1000000000 % cycle()).fold(START.toMutableMap()) {acc, _ ->  dance(acc)}.backToString()

private val START = ('a'..'p').mapIndexed { index, c -> c.toInt() to index }.toMap()

private val Instructions : List<Pair<Char, IntArray>> by lazy {
    util.readText().split(",").map {
        val first = it[0]
        val value = it.drop(1)
        when (first) {
            's' -> Pair(first, intArrayOf(value.toInt()))
            'x' -> Pair(first, value.split("/").map {it.toInt()}.toIntArray())
            'p' -> Pair(first, value.split("/").map {it[0].toInt()}.toIntArray())
            else -> Pair(first, intArrayOf())
        }
    }
}

private fun move(dancers: MutableMap<Int, Int>, inst: Pair<Char, IntArray>) : MutableMap<Int, Int>{
    fun swapByKey(vararg keys: Int) {
        val (a, b) = keys
        val tmp = dancers[a]!!
        dancers[a] = dancers[b]!!
        dancers[b] = tmp
    }
    when(inst.first) {
        's' -> dancers.mapValuesTo(dancers){ (it.value + inst.second[0]) % 16 }
        'p' -> swapByKey(*inst.second)
        'x' -> {
            val keys = dancers.filterKeys {dancers[it]!! in inst.second}.keys.toIntArray()
            swapByKey(*keys)
        }
    }
    return dancers
}

private fun dance(start: MutableMap<Int, Int>)  = Instructions.fold(start, ::move)
private fun MutableMap<Int, Int>.backToString() = this.map { it.value to it.key }.sortedBy { it.first }.map { it.second.toChar() }.joinToString("")

private tailrec fun cycle(d: MutableMap<Int, Int> = dance(START.toMutableMap()), count: Int = 1): Int =
    if(d == START)
        count
    else
        cycle(dance(d), count + 1)