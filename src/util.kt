package util

import java.io.File
import kotlin.coroutines.experimental.buildSequence

fun readLines(path: String = "src/input.txt") = File(path).readLines()
fun readText(path: String = "src/input.txt") = File(path).readText()

data class Point(val x: Int, val y: Int) {
    operator fun plus(pt: Point) = Point(x + pt.x, y + pt.y)
    operator fun unaryMinus() = Point(-x, -y)
}

val UP = Point(0, -1)
val DOWN = Point(0, 1)
val LEFT = Point(-1, 0)
val RIGHT = Point(1, 0)
val ALLDIR = setOf(UP, DOWN, LEFT, RIGHT)

fun Point.neighbors() = listOf(Point(this.x - 1, this.y + 1), Point(this.x, this.y + 1), Point(this.x + 1, this.y + 1),
        Point(this.x - 1, this.y), Point(this.x + 1, this.y),
        Point(this.x - 1, this.y - 1), Point(this.x, this.y - 1), Point(this.x + 1, this.y - 1))

fun <T> circleIndex(l: List<T>, start: Int = 0) = buildSequence {
    var idx = start
    while(true) {
        yield(idx)
        idx = (idx + 1) % l.size
    }
}

fun <T> MutableList<T>.swapRegion(start: Int = 0, length: Int) : MutableList<T> {
    val end = start + length - 1
    val middle = (start + end) / 2
    val cycle = this.size

    for(i in start..middle) {
        val t = this[i % cycle]
        this[i % cycle] = this[(end - (i - start)) % cycle]
        this[(end - (i - start)) % cycle] = t
    }
    return this
}

fun <T, R> Collection<T>.scanl(initial: R, transform: (R, T) -> R): Collection<R> {
    var acc = initial
    val c = mutableListOf(initial)

    for(e in this)
        acc = transform(acc, e).apply {c.add(this)}

    return c
}

fun <T, R> Collection<T>.scanr(initial: R, transform: (T, R) -> R): Collection<R> {
    return this.reversed().scanl(initial) {r, t -> transform(t, r) }
}

fun <T> List<List<T>>.makeGrid(filling: T, side: String = "right"): List<List<T>> {
    val max = this.maxBy {it.size }!!.size
    val ret = mutableListOf<List<T>>()
    for(l in this) {
        val padding = MutableList(max - l.size ) {filling}
        when(side) {
            "left" -> ret.add(padding + l)
            "right" -> ret.add(l + padding)
            else -> ret.add(padding.take(padding.size / 2) + l + padding.drop(padding.size / 2))
        }
    }
    return ret
}

fun <T> permutation(l: List<T>, predicate: (List<T>) -> Boolean = {true}) : Sequence<List<T>> {

    fun permutate(sofar: List<T> = listOf(), remaining: List<T>) : Sequence<List<T>> {

        return buildSequence {
            if (remaining.size == 0 && predicate(sofar))
                yield(sofar)
            else if(remaining.size > 0)
                for ((i, v) in remaining.withIndex()) {
                    val nremaining = remaining.take(i) + remaining.drop(i + 1)
                    yieldAll(permutate(sofar + v, nremaining))
                }
        }
    }

    return permutate(remaining = l)
}

fun <T> combination(l: List<T>, predicate: (List<T>) -> Boolean = {true}): Sequence<List<T>> {
    fun combine(sofar: List<T> = listOf(), remaining: List<T>) : Sequence<List<T>> {
        return buildSequence {
            if (remaining.size == 0 && predicate(sofar))
                    yield(sofar)
            else if(remaining.size > 0){
                val nremaining = remaining.drop(1)
                yieldAll(combine(sofar + remaining.first(), nremaining))
                yieldAll(combine(sofar, nremaining))
            }
        }
    }
    return combine(remaining = l)
}

fun <T> permutation2(l: List<T>, predicate: (List<T>) -> Boolean = {true}) : List<List<T>> {

    fun permutate(sofar: List<T> = listOf(), remaining: List<T>, collector: MutableList<List<T>> = mutableListOf()) : List<List<T>> {

        if(remaining.size == 0 && predicate(sofar))
            collector.add(sofar)
        else if(remaining.size > 0)
            for ((i, v) in remaining.withIndex()) {
                val nremaining = remaining.take(i) + remaining.drop(i + 1)
                permutate(sofar + v, nremaining, collector)
            }
        return collector
    }

    return permutate(remaining = l)
}

fun <T> combination2(l: List<T>, choose: (List<T>) -> Boolean = {true}): List<List<T>> {

    fun combine(sofar: List<T> = listOf(), remaining: List<T>, collector: MutableList<List<T>> = mutableListOf()) : List<List<T>> {
        if(remaining.size == 0 && choose(sofar))
                collector.add(sofar)
        else if(remaining.size > 0) {
            val nremaining = remaining.drop(1)
            combine(sofar + remaining.first(), nremaining, collector)
            combine(sofar, nremaining, collector)
        }
        return collector
    }
    return combine(remaining = l)
}