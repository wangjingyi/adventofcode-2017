package day23

import java.math.BigInteger

data class Instruction(val cmd: String, val reg: String, val value: String = "")

val Instructions: List<Instruction> by lazy {
    val ret = mutableListOf<Instruction>()
    for(line in util.readLines()) {
        val comps = line.split(" ")
        ret.add(Instruction(comps[0], comps[1], comps[2]))
    }
    ret
}

fun String.isNumber() = """-?\d+""".toRegex().matches(this)

fun solve1(): Int {
    var idx = 0
    var cnt = 0
    val registers = ('a'..'h').map { it.toString() to "0"}.toMap().toMutableMap()
    tailrec fun regval(name: String): BigInteger =
            when {
                name.isNumber() -> BigInteger(name)
                registers.getValue(name).isNumber() -> BigInteger(registers.getValue(name))
                else -> regval(name)
            }

    loop@ while(idx < Instructions.size) {
        val i = Instructions[idx]
        when (i.cmd) {
            "set" -> registers[i.reg] = regval(i.value).toString()
            "mul" -> { registers[i.reg] = (regval(i.reg) * regval(i.value)).toString(); cnt++ }
            "sub" -> registers[i.reg] = (regval(i.reg) - regval(i.value)).toString()
            "jnz" -> if(regval(i.reg).signum() != 0) {
                idx += regval(i.value).toInt()
                continue@loop
            }
        }
        idx++
    }
    return cnt
}

fun solve2() : Int {
    var b = 0
    var c = 0
    var d = 0
    var e = 0
    var f = 0
    var g = 0
    var h = 0

    b = 93
    c = b
    b *= 100
    b += 100000
    c = b
    c += 17000

    do {
        f = 1
        d = 2
        while (d < b) {
            if (b % d == 0) {
                f = 0
                break
            }
            d++
        }
        if (f == 0) {
            h++
        }
        g = b - c
        b += 17
    } while (g != 0)

    return h
}