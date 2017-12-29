package main

fun main(args: Array<String>) {
    for(l in util.combination2(listOf(1, 2, 3)) {it.isNotEmpty() && it[0] == 1})
        println(l)
}


