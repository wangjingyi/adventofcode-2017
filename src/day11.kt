package day11
import kotlin.math.abs

data class Hex(var x: Int, var y: Int, var z: Int) {
    companion object {
        val origin = Hex(0, 0, 0)
        val n = Hex(0, 1, -1)
        val nw = Hex(-1, 1, 0)
        val sw = Hex(-1, 0, 1)
        val s = Hex(0, -1, 1)
        val se = Hex(1, -1, 0)
        val ne = Hex(1, 0, -1)
    }
    operator fun plus(other: Hex) = Hex(this.x + other.x, this.y + other.y, this.z + other.z)
    // half manhattan distances
    fun distance(other: Hex = Hex.origin) = (abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)) / 2
    // or the distance can be implement as
    // because the constraint x + y + z = 0, so one component must be sum of other 2 compoents; then we can pick the big one
    // fun distance(other: Hex = Hex.origin) = listOf(abs(this.x - other.x), abs(this.y - other.y), abs(this.z - other.z)).max()!!
}

private val M: Map<String, Hex> = mapOf(
        "n" to Hex.n,
        "nw" to Hex.nw,
        "sw" to Hex.sw,
        "s" to Hex.s,
        "se" to Hex.se,
        "ne" to Hex.ne
)

fun solve1() = util.readText().split(',').map {M[it]!!}.fold(Hex.origin, Hex::plus).distance()

fun solve2() : Int {
    var pair = Pair(0, Hex.origin)
    var pos = Hex.origin
    for(cur in util.readText().split(',').map {M[it]!!}) {
        pos += cur
        val d = pos.distance()
        if(d > pair.first)
            pair = Pair(d, pos)
    }
    return pair.first
}
