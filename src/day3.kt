package day3

import util.Point
import util.neighbors
import kotlin.coroutines.experimental.buildSequence

data class Layer(val layer: Int = 0, val side: Int = 0, val start: Int = side.prevSide() * side.prevSide() + 1, val end: Int = side * side, val length: Int = end - start + 1)
private val center = Layer(0, 0, 1, 1, 1)

private fun Int.sideToLayer() = (this - 1) / 2
private fun Int.prevSide() = this - 2

private fun layer(n: Int): Layer {
    if(n == 1) return center

    val side = buildSequence {
        var start = 3
        while(n > start * start)
            start += 2
        yield(start)
    }.first()
    val layer = side.sideToLayer()

    return Layer(layer, side)
}

private fun middles(l: Layer) : List<Int> {
    val (_, _, start, _, length) = l
    val step = length / 8
    return listOf(1, 3, 5, 7).map{ start + step * it - 1}
}

private val origin = Point(0, 0)

private fun indexToPoint(index: Int): Point {

    if(index == 1) return origin

    val  l = layer(index)
    val mids = middles(l)

    val offset = l.side - 1 - 1
    val start1 = l.start
    val start2 = start1 + offset + 1
    val start3 = start2 + offset + 1

    return when (index) {
        in start1..start1 + offset -> Point(l.layer, index - mids[0])
        in start2..start2 + offset -> Point( mids[1] - index, l.layer)
        in start3..start3 + offset -> Point(-l.layer, mids[2] - index)
        else -> Point(index - mids[3], -l.layer)
    }
}

private fun pointToIndex(pt: Point): Int {
    if(pt == origin)
        return 1

    val layer = Math.max(Math.abs(pt.x), Math.abs(pt.y))
    val side = 2 * layer + 1
    val mids = middles(Layer(layer, side))
    return when {
        pt.x == layer && pt.y > -layer  -> mids[0] + pt.y
        pt.y == layer && pt.x < layer -> mids[1] - pt.x
        pt.x == -layer && pt.y < layer -> mids[2] - pt.y
        else -> mids[3] + pt.x
    }
}

fun solve1(n: Int) : Int {
    val l = layer(n)
    val ms = middles(l)
    return ms.map { Math.abs(n - it) }.min()!! + l.layer
}

fun solve2(n: Int) = buildSequence {
    var history = mutableListOf<Int>(1)
    var idx = 2
    var cur = 1
    do {
        cur = indexToPoint(idx).neighbors().map(::pointToIndex).filter { it < idx }.map { history[it - 1] }.sum()
        history.add(cur)
        idx++
    } while (cur <= n)

    yield(cur)
}.first()
