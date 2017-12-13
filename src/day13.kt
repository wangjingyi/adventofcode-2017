package day13

import kotlin.coroutines.experimental.buildSequence

fun Int.isEven() = this % 2 == 0
data class Cur(val range: Int = -1, var walks: Int = 0) {
    val pos: Int
        get() {
            val q = walks / (range - 1)
            val r = walks % (range - 1)
            return if(q.isEven()) r else range - r
        }

    val isEmpty = range == -1
    val isTop = !isEmpty && pos == 0

    fun score(depth: Int) = range * depth
}

typealias Config = MutableMap<Int, Cur>

fun Config.next() {
    for((k, v) in this) {
        this[k] = v.copy(walks = v.walks + 1)
    }
}

val M: Config by lazy {
    val c = mutableMapOf<Int, Cur>().withDefault { Cur() }
    for(line in util.readLines()) {
        val (key, value) = line.split(": ")
        c[key.toInt()] = Cur(range = value.toInt())
    }
    c
}

val max: Int by lazy { M.keys.max()!! }

fun copyState(state: Config) = HashMap(state).withDefault { Cur() }

fun totalScore(config: Config, max: Int): Int {
    var score = 0

    for(layer in (0..max)) {
        val cur = config.getValue(layer)
        if(cur.isTop)
            score += cur.score(layer)
        config.next()
    }
    return score
}

fun caught(config: Config, max: Int): Boolean {
    for(layer in (0..max)) {
        val cur = config.getValue(layer)
        if(cur.isTop)
            return true

        config.next()
    }
    return false
}

fun solve1(): Int = totalScore(copyState(M), max)

fun solve2() =
    buildSequence {
        var delay = 0
        val state = M
        while(caught(copyState(state), max)) {
            delay++
            state.next()
        }
        yield(delay)
    }.first()