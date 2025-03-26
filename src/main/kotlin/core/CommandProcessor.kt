package org.example.core

import org.example.commands.*
import java.io.File
import java.util.*

class CommandProcessor(
    private var commands: Map<String, Command>,
    private val scanner: Scanner,
    fileName: String
) {
    private lateinit var vehicleReader: VehicleReader

    constructor(scanner: Scanner, fileName: String) : this(emptyMap(), scanner, fileName)

    val collectionManager = CollectionManager(fileName)
    private val executedScripts =
        mutableSetOf<String>() // protection against recursion & may be a file reading in the file

    fun start() {
        if (commands.isEmpty()) commands = loadCommands()
        println("Transport manager 3000")
        commands["help"]?.execute(emptyList(), collectionManager)
        while (true) {
            print("> ")
            val input = scanner.nextLine().trim()
            val executeScriptRegex = "^execute_script\\s.+\$".toRegex()
            when {
                input == "exit" -> break
                executeScriptRegex.matches(input) -> executeScript(input)
                input.isEmpty() -> continue
                else -> processCommand(input)
            }
        }
    }

    private fun loadCommands(): Map<String, Command> {
        vehicleReader = VehicleReader(scanner)
        val commands = listOf(
            AddCommand(vehicleReader),
            AddIfMaxCommand(vehicleReader),
            AddIfMinCommand(vehicleReader),
            ClearCommand(),
            FilterByEnginePowerCommand(),
            HelpCommand(emptyMap()),
            InfoCommand(),
            MinByNameCommand(),
            RemoveAnyByEnginePowerCommand(),
            RemoveByIdCommand(),
            RemoveFirstCommand(),
            ShowCommand(),
            SaveCommand(),
            UpdateIdCommand(vehicleReader),

            ).associateBy { it.getName() }
        val help = HelpCommand(commands)
        val allCommands = listOf(
            help,
            AddCommand(vehicleReader),
            AddIfMaxCommand(vehicleReader),
            AddIfMinCommand(vehicleReader),
            ClearCommand(),
            FilterByEnginePowerCommand(),
            InfoCommand(),
            MinByNameCommand(),
            RemoveAnyByEnginePowerCommand(),
            RemoveByIdCommand(),
            RemoveFirstCommand(),
            ShowCommand(),
            SaveCommand(),
            UpdateIdCommand(vehicleReader),
        ).associateBy { it.getName() }
        return allCommands
    }

    private fun processCommand(input: String) {
        val parts = input.split("\\s+".toRegex())
        val command = commands[parts[0]] ?: run {
            println("Unknown command: ${parts[0]}")
            return
        }
        if (command.getName() == "execute_script") {
            if (parts.isEmpty()) {
                println("Error: The file name is not specified.")
                return
            }
            executeScript(parts[0])
            return
        }
        try {
            command.execute(parts.drop(1), collectionManager)
        } catch (e: Exception) {
            println("Error executing command: ${e.message}")
        }
    }

    private fun executeScript(input: String) {
        val parts = input.split("\\s+".toRegex())
        val filename = parts[1]
        if (filename in executedScripts) {
            println("Error: Recursion detected in script execution $filename.")
            return
        }

        val file = File(filename)
        if (!file.exists()) {
            println("Error: The $filename file was not found.")
            return
        }

        if (!file.canRead()) {
            println("Error: No rights to read the file $filename.")
            return
        }
        executedScripts.add(filename)
        val scriptScanner = Scanner(file)
        val originalScanner = vehicleReader.getScanner() // оригинальный scanner

        try {
            vehicleReader.setScanner(scriptScanner) // scanner из скрипта
            while (scriptScanner.hasNextLine()) {
                val commandLine = scriptScanner.nextLine().trim()
                if (commandLine.isNotEmpty()) {
                    println("> $commandLine")
                    processCommand(commandLine)
                }
            }
            println("File $filename was read.")
        } finally {
            vehicleReader.setScanner(originalScanner) // Восстанавливаем оригинальный scanner
            scriptScanner.close()
            executedScripts.remove(filename)
        }
    }
}