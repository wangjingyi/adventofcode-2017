package day17

fun solve1() = spinLock(steps = 314, target = 2017)
fun solve2() = afterzero(steps = 314, cutoff = 50000000).after

private fun next(cur: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
    val (cpos, cval) = cur
    val npos = (cpos + steps) % (cval + 1) + 1 //cval + 1 == the length of the list
    val nval = cval + 1
    return Pair(npos, nval)
}

private tailrec fun spinLock(path: MutableList<Int> = mutableListOf(0), cpos: Int = 0, cval: Int = 0, targetpos: Int = -1, target: Int, steps: Int): Int =
        if(targetpos >= 0 && path.lastIndex != targetpos) {
            path[targetpos + 1]
        }
        else {
            val (npos, nval) = next(Pair(cpos, cval), steps)
            val ntargetpos = if (targetpos == -1 && nval == target) npos else targetpos
            spinLock(path.apply {add(npos, nval)}, npos, nval, ntargetpos, target, steps)
        }

private data class Stop(val zeropos: Int, val after: Int)

private tailrec fun afterzero(pair: Stop = Stop(0, -1), cpos: Int = 0, cval: Int = 0, cutoff: Int, steps: Int): Stop =
        if(cval == cutoff) pair
        else {
            val (npos, nval) = next(Pair(cpos, cval), steps)
            val npair = when {
                npos == pair.zeropos -> Stop(npos + 1, pair.after) //insert at 0 position, which is never happened
                npos == pair.zeropos + 1 -> Stop(pair.zeropos, nval)       //insert at after 0 position, update the value
                else -> pair
            }

            afterzero(npair, npos, nval, cutoff, steps)
        }