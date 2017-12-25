package day24

val M: List<Pair<Int, Int>> by lazy {
    val m = mutableListOf<Pair<Int, Int>>()
    for(l in util.readLines()) {
        val (f, s) = "/".toRegex().split(l)
        m.add(Pair(f.toInt(), s.toInt()))

    }
    m
}

fun solve1() = search(M)
fun solve2() = search2(M)

fun search(components: List<Pair<Int, Int>>, search: Int = 0) : Int {
    val nodes = components.filter { it.first == search || it.second == search }
    return search(components, nodes, search)
}

fun search(components: List<Pair<Int, Int>>, nodes: List<Pair<Int, Int>>, search: Int): Int {
    var m = 0
    for(node in nodes) {
        val remaining = components.toMutableList().apply {remove(node)}
        var nv = search(remaining, if(node.first != search) node.first else node.second) + node.first + node.second
        if(nv > m)
            m = nv
    }
    return m
}

fun search2(components: List<Pair<Int, Int>>, search: Int = 0) : Pair<Int, Int> {
    val nodes = components.filter { it.first == search || it.second == search }
    return search2(components, nodes, search)
}

fun search2(components: List<Pair<Int, Int>>, nodes: List<Pair<Int, Int>>, search: Int): Pair<Int, Int> {
    var m = Pair(0, 0)
    for(node in nodes) {
        val remaining = components.toMutableList().apply {remove(node)}
        var (v, l) = search2(remaining, if(node.first != search) node.first else node.second)
        v += node.first + node.second
        l += 1
        if(l > m.second || (l == m.second && v > m.first))
            m = Pair(v, l)
    }
    return m
}
