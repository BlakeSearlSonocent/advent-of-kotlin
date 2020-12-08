package console

class Console(input: List<String>) {
    private var memory = emptyList<Instruction>()
    private var pointer = 0
    var acc = 0
    private val visitedPoints = mutableSetOf<Int>()

    init {
        memory = writeToMemoryFromInput(input)
    }

    private fun writeToMemoryFromInput(input: List<String>): List<Instruction> {
        return input.map { it.split(" ") }
            .map {
                val opCode = OpCode.from(it[0])!!
                val arg = it[1].toInt()
                Instruction(opCode, arg, this)
            }
    }

    fun run(): Boolean {
        return runNextInstruction()
    }

    private fun runNextInstruction(): Boolean {
        if (visitedPoints.contains(pointer)) return false

        if (pointer == memory.size) return true

        visitedPoints.add(pointer)
        val instruction = memory[pointer]
        instruction.performInstruction()
        return runNextInstruction()
    }

    fun clear() {
        memory = emptyList()
        pointer = 0
        acc = 0
        visitedPoints.clear()
    }

    class Instruction(private val opCode: OpCode, private val arg: Int, private val console: Console) {
        fun performInstruction() = opCode.doOp(arg, console)
    }

    enum class OpCode(i: Int) {
        nop(0) {
            override fun doOp(arg: Int, console: Console) {
                console.pointer++
            }
        },
        acc(1) {
            override fun doOp(arg: Int, console: Console) {
                console.acc += arg
                console.pointer++
            }
        },
        jmp(2) {
            override fun doOp(arg: Int, console: Console) {
                console.pointer += arg
            }
        },
        err(99) {
            override fun doOp(arg: Int, console: Console) {
                println("We got a problem here!!!")
            }
        };

        abstract fun doOp(arg: Int, console: Console)

        companion object {
            fun from(type: String) = values().find { it.name == type } ?: err
        }
    }
}

