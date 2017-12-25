package day25

fun solve1() : Int {
    var i = 0;
    val steps = 12964419
    val tape = mutableMapOf<Int, Int>().withDefault { 0 }
    var state : State = A(tape, 0)
    while(i < steps) {
        state = state.next()
        i++
    }
    return state.count()
}

sealed class State(open val tape: MutableMap<Int, Int>, open val cur: Int){
    abstract fun next() : State
    fun right(cur: Int) = cur + 1
    fun left(cur: Int) = cur - 1
    fun count(): Int = tape.values.count { it == 1 }
}

data class A(override val tape: MutableMap<Int, Int>, override val cur: Int) : State(tape, cur) {
    override fun next() : State {
        val cv = tape.getValue(cur)

        if(cv == 0) {
            tape[cur] = 1
            return B(tape, right(cur))
        }
        else {
            tape[cur] = 0
            return F(tape, right(cur))
        }
    }
}

data class B(override val tape: MutableMap<Int, Int>, override val cur: Int) : State(tape, cur) {
    override fun next() : State {
        val cv = tape.getValue(cur)
        if(cv == 0) {
            tape[cur] = 0
            return B(tape, left(cur))
        }
        else {
            tape[cur] = 1
            return C(tape, left(cur))
        }
    }
}

data class C(override val tape: MutableMap<Int, Int>, override val cur: Int) : State(tape, cur) {
    override fun next() : State {
        val cv = tape.getValue(cur)

        if(cv == 0) {
            tape[cur] = 1
            return D(tape, left(cur))
        }
        else {
            tape[cur] = 0
            return C(tape, right(cur))
        }
    }
}

data class D(override val tape: MutableMap<Int, Int>, override val cur: Int) : State(tape, cur) {
    override fun next() : State {
        val cv = tape.getValue(cur)

        if(cv == 0) {
            tape[cur] = 1
            return E(tape, left(cur))
        }
        else {
            tape[cur] = 1
            return A(tape, right(cur))
        }
    }
}

data class E(override val tape: MutableMap<Int, Int>, override val cur: Int) : State(tape, cur) {
    override fun next() : State {
        val cv = tape.getValue(cur)

        if(cv == 0) {
            tape[cur] = 1
            return F(tape, left(cur))
        }
        else {
            tape[cur] = 0
            return D(tape, left(cur))
        }
    }
}

data class F(override val tape: MutableMap<Int, Int>, override val cur: Int) : State(tape, cur) {
    override fun next() : State {
        val cv = tape.getValue(cur)

        if(cv == 0) {
            tape[cur] = 1
            return A(tape, right(cur))
        }
        else {
            tape[cur] = 0
            return E(tape, left(cur))
        }
    }
}
