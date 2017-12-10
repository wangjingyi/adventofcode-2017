package util

import java.io.File
import kotlin.coroutines.experimental.buildSequence

fun readLines(path: String = "src/input.txt") = File(path).readLines()
fun readText(path: String = "src/input.txt") = File(path).readText()

data class Point(val x: Int, val y: Int)

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
