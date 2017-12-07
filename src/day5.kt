package day5

private tailrec fun jump(arr: MutableList<Int>, idx: Int = 0, steps: Int = 0, update: (Int) -> Int) : Int =
        if(idx < 0 || idx >= arr.size)
            steps
        else {
            val offset = arr[idx]
            val newIdx = offset + idx
            arr[idx] += update(offset)
            jump(arr, newIdx, steps + 1, update)
        }

fun solve1() : Int {
    val l = util.readLines().map {it.toInt()}.toMutableList()
    return jump(l) {1}
}

fun solve2() : Int {
    val l = util.readLines().map {it.toInt()}.toMutableList()
    return jump(l) {if(it >= 3) -1 else 1 }
}