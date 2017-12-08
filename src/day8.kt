//ie dec -551 if o <= -2098
package day8

private val pat = """(?<reg1>[a-z]+)\s+(?<op>(?:inc|dec))\s+(?<n1>-?\d+)\s+if\s+(?<reg2>[a-z]+)\s+(?<rel>[<>=!]+)\s+(?<n2>-?\d+)""".toRegex()
private val M  = mutableMapOf<String, Int>().withDefault {0}
private var max = 0

private fun operate(groups: MatchResult.Destructured) {
    val (reg1, op, r1, reg2, rel, r2) = groups

    val n1 = r1.toInt()
    val n2 = r2.toInt()
    val r2v = M.getValue(reg2)
    val flag = when(rel) {
        "==" -> r2v == n2
        ">=" -> r2v >= n2
        "<=" -> r2v <= n2
        "!=" -> r2v != n2
        "<"  -> r2v < n2
        ">"  -> r2v > n2
        else -> false
    }
    if(!flag) return

    M[reg1] = if (op == "inc") M.getValue(reg1) + n1 else M.getValue(reg1) - n1
    if(M[reg1]!! > max) max = M[reg1]!!
}

fun solve1() : Int {
    util.readLines().asSequence().forEach { operate(pat.matchEntire(it)!!.destructured) }
    return M.values.max()!!
}

fun solve2() : Int {
    util.readLines().asSequence().forEach { operate(pat.matchEntire(it)!!.destructured) }
    return max
}