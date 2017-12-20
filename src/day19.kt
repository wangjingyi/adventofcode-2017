package day19

import util.*

val M: List<List<Char>> by lazy {
    mutableListOf<List<Char>>().apply {
        for (l in util.readLines())
            add(l.toList())
    }.makeGrid(' ')
}


val START: Point by lazy {
    Point(M[0].indexOf('|'), 0)
}

fun newDirection(pt: Point, curdir: Point): Point {
    for(d in util.ALLDIR - (-curdir)) {
        try {
            val c = M[pt.y + d.y][pt.x + d.x]
            if (c != ' ')
                return d
        }
        catch(e: Exception) {}
    }

    return curdir
}

fun isOutside(pt: Point) = pt.x !in 0 until M[0].size || pt.y !in 0 until M.size

tailrec fun next(cpt: Point, cdir: Point, sb: StringBuilder = StringBuilder(), cnt: Int = 0): Pair<String, Int> {
    if(isOutside(cpt))
        return Pair(sb.toString(), cnt)

    var c = M[cpt.y][cpt.x]
    var ndir = cdir
    when {
        c == ' ' -> return Pair(sb.toString(), cnt)
        c == '+' -> ndir = newDirection(cpt, cdir)
        c.isLetter() -> sb.append(c)
    }

    val npt = cpt + ndir
    return next(npt, ndir, sb, cnt + 1)
}

fun solve() = next(START, DOWN)