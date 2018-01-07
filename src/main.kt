package main

import kotlin.properties.Delegates
fun main(args: Array<String>) {
    for(l in util.permutate(listOf(1, 2, 3)))
        println(l)

    for(l in util.combine(listOf(1, 2, 3)))
        println(l)
}

