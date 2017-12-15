package day14

fun solve1(str: String = "hfdlxzhv") = disk(str).fold(0) {acc, s -> acc + s.count{it == '1'}}
fun solve2(str: String = "hfdlxzhv") = makeSet(str).values.toSet().size

private fun disk(str: String) : List<MutableList<Char>> {
    var ret = mutableListOf<MutableList<Char>>()
    for(i in 0..127) {
        val bstr = day10.toHash(str + '-' + i.toString()).map {
            val n = java.lang.Integer.parseInt(it.toString(), 16)
            String.format("%4s", Integer.toBinaryString(n)).replace(' ', '0')
        }.joinToString("").toMutableList()
        ret.add(bstr)
    }
    return ret
}

fun makeSet(str: String): Map<Pair<Int,Int>, Pair<Int, Int>> {
    val m = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
    val d = disk(str)

    for((i, row) in d.withIndex())
        for((j, ch) in row.withIndex()) {
            if(ch == '1') {
                val p = Pair(i, j)
                update(d, i, j, p, m)
            }
        }
    return m
}

fun update(disk: List<MutableList<Char>>, i: Int, j: Int, p: Pair<Int, Int>, m: MutableMap<Pair<Int,Int>, Pair<Int, Int>>) {
    when {
        i < 0 || i >= disk.size || j < 0 || j >= disk[0].size -> return
        disk[i][j] == '0' -> return
        else -> {
            disk[i][j] = '0'
            m[Pair(i, j)] = p
            update(disk, i - 1, j, p, m)
            update(disk, i + 1, j, p, m)
            update(disk, i, j - 1, p, m)
            update(disk, i, j + 1, p, m)
        }
    }
}