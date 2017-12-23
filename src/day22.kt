package day22

import util.*

fun solve2() = move(M.toMutableMap().withDefault { '.' }, n = 10000000)

val M: Map<Point, Char> by lazy {
    val m = mutableMapOf<Point, Char>().withDefault { '.' }
    for((y, l) in util.readLines().withIndex()) {
        for((x, ch) in l.withIndex()) {
            if(ch == '#')
                m[Point(x, y)] = '#'
        }
    }
    m
}

val START_POS = Point(readLines().count() / 2, readLines().count() / 2)

fun update(ch: Char) =
        when (ch) {
            '.' -> 'w'
            'w' -> '#'
            '#' -> 'f'
            'f' -> '.'
            else -> 'u'
        }

fun turn(ch: Char, dir: Point) =
        when {
            ch == '.' -> toLeft(dir)
            ch == '#' -> toRight(dir)
            ch == 'f' -> toBack(dir)
            else -> dir
        }

fun toRight(dir: Point) =
        when(dir) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            else -> dir
        }

fun toLeft(dir: Point) =
        when(dir) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
            else -> dir
        }

fun toBack(dir: Point) =
        when(dir) {
            UP -> DOWN
            DOWN -> UP
            RIGHT -> LEFT
            LEFT -> RIGHT
            else -> dir
        }


tailrec fun move(g: MutableMap<Point, Char>, cp: Point = START_POS, cdir: Point = UP, n: Int, cnt: Int = 0) : Int{
    if(n == 0) {
        return cnt
    }

    val (x, y) = cp
    val ch = g.getValue(cp)
    var ncnt = cnt
    var ndir = turn(ch, cdir)
    if(ch == 'w')
        ncnt = cnt + 1
    g[cp] = update(ch)
    val ncp = cp + ndir

    return move(g, ncp, ndir, n - 1, ncnt)
}

