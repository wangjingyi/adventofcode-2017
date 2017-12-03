package util

import java.io.File

fun readLines(path: String = "src/input.txt") = File(path).readLines()

data class Point(val x: Int, val y: Int)

fun Point.neighbors() = listOf(Point(this.x - 1, this.y + 1), Point(this.x, this.y + 1), Point(this.x + 1, this.y + 1),
        Point(this.x - 1, this.y), Point(this.x + 1, this.y),
        Point(this.x - 1, this.y - 1), Point(this.x, this.y - 1), Point(this.x + 1, this.y - 1))