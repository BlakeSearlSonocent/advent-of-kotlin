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
                val opCode = OpCode.from(it[0].toUpperCase())
                val arg = it[1].toInt()
                Instruction(opCode, arg, this)
            }
    }

    fun run(): Boolean {
        while (true) {
            if (visitedPoints.contains(pointer)) return false
            if (pointer == memory.size) return true

            visitedPoints.add(pointer)
            val instruction = memory[pointer]
            instruction.performInstruction()
        }
    }

    fun resetState() {
        pointer = 0
        acc = 0
        visitedPoints.clear()
    }

    fun runCorruptionFix() {
        memory.forEach { instruction ->
            var oldOpCode = instruction.opCode
            if (oldOpCode == OpCode.NOP || oldOpCode == OpCode.JMP) {

                when (oldOpCode) {
                    OpCode.NOP -> instruction.opCode = OpCode.JMP
                    OpCode.JMP -> instruction.opCode = OpCode.NOP
                }

                if (run()) {
                    println("Corruption fix found, acc is $acc")
                    return
                } else {
                    resetState()
                    instruction.opCode = oldOpCode
                }
            }
        }

        println("No corruption fix found")
    }

    class Instruction(var opCode: OpCode, private val arg: Int, private val console: Console) {
        fun performInstruction() = opCode.doOp(arg, console)
    }

    enum class OpCode {
        NOP {
            override fun doOp(arg: Int, console: Console) {
                console.pointer++
            }
        },
        ACC {
            override fun doOp(arg: Int, console: Console) {
                console.acc += arg
                console.pointer++
            }
        },
        JMP {
            override fun doOp(arg: Int, console: Console) {
                console.pointer += arg
            }
        },
        ERR {
            override fun doOp(arg: Int, console: Console) {
                println("We got a problem here!!!")
            }
        };

        abstract fun doOp(arg: Int, console: Console)

        companion object {
            fun from(type: String) = values().find { it.name == type } ?: ERR
        }
    }
}

