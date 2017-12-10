package day10
import java.util.*
import util.swapRegion

fun solve1() : Int {
    val (_, _, value) = hash((0..255).toMutableList(), lengths = ArrayDeque(listOf(46,41,212,83,1,255,157,65,139,52,39,254,2,86,0,204)))
    return value[0] * value[1]
}

fun solve2() : String {
    var lens = ArrayDeque(lengths())
    var d = Round(start = 0, skip = 0, value = (0..255).toMutableList())
    for(i in 1..64) {
        d = hash(d.value.toMutableList(), d.start, d.skip, lens.clone())
    }
    var dense = (0..15).map {d.value.subList(it * 16, it * 16 + 16)}.map(::xors)
    return dense.map {String.format("%02X", it)}.joinToString("")
}

data class Round(val start: Int, val skip: Int, val value: List<Int>)

private tailrec fun hash(l: MutableList<Int>, start: Int = 0, skip: Int = 0, lengths: Queue<Int>): Round {
    if (lengths.size == 0)
        return Round(start % l.size, skip, l)

    val len = lengths.remove()
    l.swapRegion(start % l.size, len)
    return hash(l, (start + len + skip) % l.size, skip + 1, lengths)
}


private fun lengths(input: String = "46,41,212,83,1,255,157,65,139,52,39,254,2,86,0,204") = input.map {it.toInt()}.plus(arrayOf(17, 31, 73, 47, 23))

private fun xors(l: List<Int>) = l.reduce{a, b -> a xor b}