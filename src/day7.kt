package day7

import java.util.*

private val M : Map<String, Prog> by lazy {
    buildMap()
}


data class Prog(val name: String, var weight: Int = 0, var parent: Prog? = null, var children: List<Prog> = listOf())

private fun buildMap() : Map<String, Prog> {
    val m = mutableMapOf<String, Prog>()
    util.readLines().asSequence().map {"""([a-z]+)\s*\((\d+)\)\s*(?:->)*\s*([a-z,\s]+)*""".toRegex().matchEntire(it) }.forEach {
        val childrenNames = if (it?.groupValues?.getOrNull(3).isNullOrBlank())
            listOf<String>()
        else
            it!!.groupValues[3].split(""",\s*""".toRegex()).map { it.trim() }
        
        val name = it!!.groupValues[1]
        val weight = Integer.parseInt(it!!.groupValues[2])
        val p = Prog(name, weight)
        p.children = childrenNames.map { Prog(name = it, parent = p)}
        m[name] = p

    }
    return m.toMap()
}

fun solve2() : Int {
    val root: Prog = M.get(solve1().first())!!
    var stack = Stack<Prog>().apply {this.push(root)}

    while(!stack.isEmpty()) {
        var node = stack.peek()
        when {
            node.children.size == 0 -> stack.pop()
            node.children.first().weight == 0 -> for (child in node.children) {
                val lookup = M.get(child.name)!!.copy()
                child.weight = lookup.weight
                child.children = lookup.children
                stack.push(child)
            }
            else ->{
                var first = node.children.first()
                for (child in node.children) {
                    val delta = child.weight - first.weight
                    if(delta == 0)
                        node.weight += child.weight
                    else
                        return if(first.weight == node.children.last().weight)
                            M.get(child.name)!!.weight - delta
                        else M.get(first.name)!!.weight + delta
                }
                stack.pop()
            }
        }
    }
    return 0
}

fun solve1() : Set<String> = with(M) { keys.toSet() - values.flatMap {it.children}.map {it.name}.toSet() }
