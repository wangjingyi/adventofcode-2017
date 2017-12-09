package day9

import util.readText
import java.io.StringReader
import java.util.Stack

private fun trash(s: Stack<Char>) = !s.isEmpty() && s.peek() == '<'
private fun shouldCount(c: Char) = c !in charArrayOf('!', '>')

fun solve() : Pair<Int, Int> {
    var score = 0
    var count = 0
    val s = Stack<Char>()  // only '{' and '<' can be pushed
    val sr = StringReader(readText())
    var ch = sr.read()

    while(ch != -1) {
        val c = ch.toChar()
        if(trash(s) && shouldCount(c)) count += 1
        when(c) {
            '{' -> if(!trash(s)) s.push(c)
            '<' -> if(!trash(s)) s.push(c)
            '!' -> if(trash(s)) sr.skip(1)
            '>' -> if(trash(s)) s.pop()
            '}' -> if(!trash(s)){ score += s.size; s.pop()}
        }
        ch = sr.read()
    }

    return Pair(score, count)
}