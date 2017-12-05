package day4

fun solve1() = util.readLines().asSequence().map {"""\s+""".toRegex().split(it)}.filter { it.size == it.toSet().size }.count()

fun solve2() =  util.readLines().asSequence().map {"""\s+""".toRegex().split(it)}.filter { l -> l.size == l.map {it.toSet()}.toSet().size }. count()