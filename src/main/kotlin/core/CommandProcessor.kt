import java.util.Scanner

class CommandProcessor(
    private val commands: Map<String, BaseCommand>,
    private val scanner: Scanner
) {
    fun start() {
        println("Vehicle Collection Manager")
        printHelp()

        while (true) {
            print("> ")
            val input = scanner.nextLine().trim()

            when {
                input == "exit" -> break
                input.isEmpty() -> continue
                else -> processCommand(input)
            }
        }
    }

    private fun processCommand(input: String) {
        val parts = input.split("\\s+".toRegex())
        val command = commands[parts[0]] ?: run {
            println("Unknown command: ${parts[0]}")
            return
        }

        try {
            command.execute(parts.drop(1))
        } catch (e: Exception) {
            println("Error executing command: ${e.message}")
        }
    }

    private fun printHelp() {
        println("Available commands:")
        commands.values.forEach {
            println("  ${it.getName().padEnd(10)} - ${it.getDescription()}")
        }
    }
}