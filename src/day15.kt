package day15

fun solve1(a: Int = 618, b: Int = 814, times: Int = 40 * 1000000): Int = count(a, b, times)
fun solve2(a: Int = 618, b: Int = 814, times: Int = 5 * 1000000): Int = count(a, b, times, {it % 4 != 0}, {it % 8 != 0})

private fun count(a: Int = 618, b: Int = 814, times: Int = 40 * 1000000, p1: (Int) -> Boolean = {false}, p2: (Int) -> Boolean = {false }): Int{
    var cnt = 0
    val mask = 0x0000ffff

    var a1: Long = a.toLong()
    var a2: Long = b.toLong()
    for(i in 0 until times) {
        do {
            a1 = a1 * 16807 % 2147483647
        } while(p1(a1.toInt()))

        do {
            a2 = a2 * 48271 % 2147483647
        } while(p2(a2.toInt()))

        if((a1.toInt() and mask) == (a2.toInt() and mask))
            cnt++
    }
    return cnt
}

