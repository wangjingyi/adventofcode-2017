package day18

import java.math.BigInteger
import java.util.*

data class Instruction(val cmd: String, val reg: String, val value: String = "")

val Instructions: List<Instruction> by lazy {
    val ret = mutableListOf<Instruction>()
    for(line in util.readLines()) {
        val comps = line.split(" ")
        if (comps.size == 3)
            ret.add(Instruction(comps[0], comps[1], comps[2]))
        else
            ret.add(Instruction(comps[0], comps[1]))
    }
    ret
}

fun String.isNumber() = """-?\d+""".toRegex().matches(this)

fun solve1(): String {
    var idx = 0
    val registers = mutableMapOf<String, String>().withDefault { "0" }
    val s = Stack<Pair<String, String>>()

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
            "add" -> registers[i.reg] = (regval(i.reg) + regval(i.value)).toString()
            "mul" -> registers[i.reg] = (regval(i.reg) * regval(i.value)).toString()
            "mod" -> registers[i.reg] = (regval(i.reg) % regval(i.value)).toString()
            "jgz" -> if(regval(i.reg).signum() == 1) {
                idx += regval(i.value).toInt()
                continue@loop
            }
            "snd" -> s.push(Pair(i.reg, regval(i.reg).toString()))
            "rcv" -> if(regval(i.reg).signum() != 0) break@loop
        }
        idx++
    }
    return s.peek().second
}

fun solve2() : Int {
    val q1 = ArrayDeque<BigInteger>()
    val q2 = ArrayDeque<BigInteger>()
    val w1 = Worker(id = "0", snd = q1, rcv = q2)
    val w2 = Worker(id = "1", snd = q2, rcv = q1)
    while(w1.cango || w2.cango) {
        if(w1.cango) w1.run()
        else if(w2.cango) w2.run()
    }
    return w2.count
}

class Worker(val id: String, var cur: Int = 0, var count: Int = 0, val snd: Queue<BigInteger>, val rcv: Queue<BigInteger>) {

    val registers = mutableMapOf<String, String>().withDefault { if(it == "p") id else "0" }

    val cango: Boolean
        get() = this.cur < Instructions.size && (Instructions[cur].cmd != "rcv" || !rcv.isEmpty())

    tailrec fun regval(name: String): BigInteger =
        when {
            name.isNumber() -> BigInteger(name)
            registers.getValue(name).isNumber() -> BigInteger(registers.getValue(name))
            else -> regval(name)
        }

    fun run() {
        val i = Instructions[cur]
        when (i.cmd) {
            "set" -> registers[i.reg] = regval(i.value).toString()
            "add" -> registers[i.reg] = (regval(i.reg) + regval(i.value)).toString()
            "mul" -> registers[i.reg] = (regval(i.reg) * regval(i.value)).toString()
            "mod" -> registers[i.reg] = (regval(i.reg) % regval(i.value)).toString()
            "jgz" -> if(regval(i.reg).signum() == 1) {
                cur += regval(i.value).toInt()
                return  //skip go to next instructin cur++
            }
            "snd" -> {
                snd.add(regval(i.reg))
                count++
            }
            "rcv" -> registers[i.reg] = rcv.poll()!!.toString()
        }
        cur++
    }
}
