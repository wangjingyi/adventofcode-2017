package day21

import kotlin.math.sqrt

fun solve1() = count(end = 5)
fun solve2() = count(end = 18)

val M: Map<List<List<Char>>, List<List<Char>>> by lazy {
    val m = mutableMapOf<List<List<Char>>, List<List<Char>>>()
    for(l in util.readLines()) {
        val (key, value) = "\\s*=>\\s*".toRegex().split(l)
        val lss = allpattern(key.split("/").map {it.toList()})
        for(ls in lss) {
            m[ls] = value.toCharList()
        }
    }
    m
}

fun String.toCharList() = this.split("/").map{ it.toList() }

val initial: List<List<Char>> = ".#./..#/###".toCharList()

tailrec fun count(g: List<List<Char>> = initial, end: Int = 5): Int {
    if(end == 0)
        return g.sumBy { it.count{it == '#'} }
    else {
        val smGrid = toSmallGrid(g)
        val ng = toBigGrid(smGrid)
        return count(ng, end - 1)
    }
}
fun toSmallGrid(g: List<List<Char>>) : List<List<List<Char>>> {
    val n = if(g[0].size % 2 == 0) 2 else 3
    val ret = mutableListOf<List<List<Char>>>()

    for(row in 0..g.size - 1 step n)
        for(col in 0..g[0].size - 1 step n) {
            val k = slice(g, row, col, n)
            ret.add(M[k]!!)
        }
    return ret
}

fun <T> slice(grid: List<List<T>>, row: Int, col: Int, size: Int) : List<List<T>>{
    val ret = mutableListOf<List<T>>()
    for(i in grid.slice(row until row + size))
        ret.add(i.slice(col until col + size))
    return ret
}

fun <T> toBigGrid(lists: List<List<List<T>>>) : List<List<T>>{

    fun merge(sg: List<List<List<T>>>) : List<List<T>> {
        val ret = mutableListOf<List<T>>()
        for(i in 0 until sg[0].size) {
            val row = mutableListOf<T>()
            for (j in 0 until sg.size) {
                row.addAll(sg[j][i])
            }
            ret.add(row)
        }
        return ret
    }

    val n = sqrt(lists.size.toDouble()).toInt() //each row has n 3x3 or 2x2 squares

    val ret = mutableListOf<List<T>>()

    for(row in lists.chunked(n){merge(it)})
        ret.addAll(row)
    return ret
}

fun <T>  List<List<T>>.rotateR() : List<List<T>> {
    val ret = mutableListOf<MutableList<T>>()
    for(i in 0 until this[0].size) {
        val x = mutableListOf<T>()
        for(j in this.size - 1 downTo 0)
            x.add(this[j][i])
        ret.add(x)
    }
    return ret
}

fun <T>  List<List<T>>.rotateL() : List<List<T>> {
    val ret = mutableListOf<MutableList<T>>()
    for(i in this[0].size - 1 downTo 0) {
        val x = mutableListOf<T>()
        for(j in 0 until this.size)
            x.add(this[j][i])
        ret.add(x)
    }
    return ret
}

fun <T>  List<List<T>>.rotate() : List<List<T>> {
    val ret = mutableListOf<MutableList<T>>()
    for(i in 0 until this[0].size) {
        val x = mutableListOf<T>()
        for(j in 0 until this.size)
            x.add(this[j][i])
        ret.add(x)
    }
    return ret
}

fun <T> List<List<T>>.flipV() = this.rotate().rotateL()
fun <T> List<List<T>>.flipH() = this.rotate().rotateR()


fun <T> allflip(start: List<List<T>>) = listOf(start, start.flipH(), start.flipV(), start.flipH().flipV())
fun <T> allrotate(start: List<List<T>>) = listOf(start, start.rotateR(), start.rotateR().rotateR(), start.rotateR().rotateR().rotateR())

fun <T> allpattern(start: List<List<T>>): Set<List<List<T>>> {
    val ret = mutableListOf<List<List<T>>>()
    for(flip in allflip(start)) {
        ret.addAll(allrotate(flip))
    }
    return ret.toSet()
}
