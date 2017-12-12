package day12

private val M: MutableMap<String, MutableSet<String>> by lazy {
    var m = mutableMapOf<String, MutableSet<String>>().withDefault { mutableSetOf() }
    util.readLines().map {it.split("<->")}.forEach {
        var left = it[0].trim()
        it[1].split(",").forEach {
            val e = it.trim()
            m[left] = m.getValue(left).apply {this.add(e)}
        }
    }
    m
}

private fun count(id: String = "0", todo: MutableSet<String> = mutableSetOf(), cnt: Int = 0, sofar: MutableSet<String> = mutableSetOf()) : Int =
        if(id !in sofar) {
            countToDo(todo.apply {this += M[id]!!},  cnt + 1, sofar.apply { this += id })
        }
        else countToDo(todo, cnt, sofar)


private fun countToDo(todo: MutableSet<String>, cnt: Int, sofar: MutableSet<String>) : Int =
        if(todo.isEmpty()) cnt else count(todo.first(), todo.apply { this -= this.first() }, cnt, sofar)


fun solve1() = count()

fun solve2() : Int {
    val sofar = mutableSetOf<String>()
    count(sofar = sofar)
    var group = 1
    for((k, v) in M) {
        if(sofar.intersect(v + k).isEmpty()) {
            count(id=k, sofar=sofar)
            group += 1
        }
    }
    return group
}