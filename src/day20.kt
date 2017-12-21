package day20

import kotlin.math.abs
import kotlin.math.sqrt

private val M: List<Particle> by lazy {
    val m = mutableListOf<Particle>()
    for(l in util.readLines()) {
        val arr = "<([^>]+),([^>]+),([^>]+)>".toRegex().findAll(l).map {
            Coor(it.groupValues[1].toInt(), it.groupValues[2].toInt(), it.groupValues[3].toInt())
        }.toList()
        m.add(Particle(arr[0], arr[1], arr[2]))
    }
    m
}

data class Coor(val x: Int, val y: Int, val z: Int) {
    operator fun plus(c: Coor) = Coor(x + c.x, y + c.y, z + c.z)
    operator fun minus(c: Coor) = Coor(x - c.x, y - c.y, z - c.z)
}

fun solve1() = M.withIndex().minWith(compareBy ({ abs(it.value.a.x) + abs(it.value.a.y) + abs(it.value.a.z) }, { abs(it.value.v.x) + abs(it.value.v.y) + abs(it.value.v.z) }))!!.index

data class Particle(var p: Coor, var v: Coor, var a: Coor) {
    fun next() {
        v = v + a
        p = p + v
    }
}

fun solve2() : Int {
    var m = M.toMutableList()
    for(i in 0..1000) {
        m.map { it.next() }
        m = m.groupBy { it.p }.filter { it.value.size == 1 }.values.flatten().toMutableList()
    }
    return m.size
}

// the following is based on the pure math. to compute the collision time on x, y and z direction.
// if the collision time for x, y and z are the same, then collision happen. Unfortunately,
// because of the double precision, I am not able to get the accurate number. The answer
// vary based on the standard of small enough.
//
//// x = x0 + v0 * t + t(t+1)/2 * a0
//data class Particle(val p: Coor, val v: Coor, val a: Coor) {
//    fun time(dp: Int, dv: Int, da: Int) : Double {
//        val a = da
//        val b = da + 2 * dv
//        val c = 2 * dp
//        val det = b * b -  a * c * 4
//        if(a == 0 && b == 0)
//            return if(c == 0) -2.0 else -1.0
//
//        if(det < 0)
//            return -1.0
//        else {
//            val st = sqrt(det.toDouble())
//            val s1: Double = if (a == 0 && b != 0) (-c / b.toDouble()) else ((-b) - st)/(2 * a)
//            val s2: Double = if (a == 0 && b != 0) (-c / b.toDouble()) else ((-b) + st)/(2 * a)
//
//            if(s1 > 0)
//                return s1
//            else if(s2 > 0)
//                return s2
//            else
//                return -1.0
//        }
//    }
//
//    fun collideTime(other: Particle): Int {
//        val smallenough = 0.05
//        val dp = p - other.p
//        val dv = v - other.v
//        val da = a - other.a
//
//        val xtime = time(dp.x, dv.x, da.x)
//        val ytime = time(dp.y, dv.y, da.y)
//        val ztime = time(dp.z, dv.z, da.z)
//        val s = mutableSetOf(xtime, ytime, ztime).filter{it != -2.0}
//        val ave = s.average()
//        if(s.all { abs(it - ave) < smallenough } && s[0] != -1.0)
//            return ave.toInt()
//        else
//            return -1
//    }
//}
//
//
//fun solve2() : Int {
//    val m = mutableMapOf<Int, MutableList<Pair<Particle, Particle>>>().withDefault { mutableListOf() }
//
//    for(i in M.withIndex()) {
//        for(j in i.index + 1 until M.size) {
//            val t = i.value.collideTime(M[j])
//            if(t != -1) {
//                val l = m.getValue(t)
//                m[t] = l.apply {add(Pair(i.value, M[j]))}
//            }
//        }
//    }
//
//    val dead = mutableMapOf<Particle, Int>().withDefault { -1 }
//
//    for((t, l) in m.toSortedMap()) {
//        loop@for((p1, p2) in l) {
//            val t1 = dead.getValue(p1)
//            val t2 = dead.getValue(p2)
//            when {
//                t1 == -1 && t2 == -1 -> { dead[p1] = t; dead[p2] = t } //both not there yet
//                t1 != -1 && t1 < t -> continue@loop  // p1 is there earlier
//                t2 != -1 && t2 < t -> continue@loop  // p2 is there earlier
//                t1 != -1 && t1 == t -> { dead[p1] = t; dead[p2] = t } // p1 is there but in this round (time)
//                t2 != -1 && t2 == t -> { dead[p1] = t; dead[p2] = t } // p2 is there but in this round (time)
//                else -> continue@loop
//            }
//        }
//    }
//    return M.toSet().size - dead.keys.size
//}








