package org.example.core

import IOManager
import org.example.commands.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class CommandProcessor(
    private var commands: Map<String, Command>,
    private val ioManager: IOManager,
    fileName: String
) {
    private lateinit var vehicleReader: VehicleReader

    constructor(ioManager: IOManager, fileName: String) : this(emptyMap(), ioManager, fileName)

    val collectionManager = CollectionManager(fileName)
    private val maxRecursionDepth = 5
    private var recursionDepth = 0
    private val executedScripts =
        mutableSetOf<String>() // protection against recursion & may be a file reading in the file

    fun start() {
        if (commands.isEmpty()) commands = loadCommands()
        ioManager.outputLine("Transport manager 3000")
        commands["help"]?.execute(emptyList(), collectionManager, ioManager)
        while (true) {
            print("> ")
            val input = ioManager.readLine().trim()
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
        vehicleReader = VehicleReader(ioManager)
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
            ioManager.outputLine("Unknown command: ${parts[0]}")
            return
        }
        if (command.getName() == "execute_script") {
            if (parts.isEmpty()) {
                ioManager.outputLine("Error: The file name is not specified.")
                return
            }
            executeScript(parts[0])
            return
        }
        try {
            command.execute(parts.drop(1), collectionManager, ioManager)
        } catch (e: Exception) {
            ioManager.outputLine("Error executing command: ${e.message}")
        }
    }

    private fun executeScript(input: String) {
        val parts = input.split("\\s+".toRegex())
        if (parts.size < 2) {
            ioManager.error("Syntax: execute_script <filename>")
            return
        }

        val filename = parts[1]
        if (filename in executedScripts) {
            ioManager.error("Recursion detected: $filename")
            return
        }

        if (recursionDepth >= maxRecursionDepth) {
            throw StackOverflowError("Max script recursion depth ($maxRecursionDepth) exceeded")
        }

        val path = Paths.get(filename)
        if (!Files.exists(path)) {
            ioManager.error("File not found: $filename")
            return
        }

        if (!Files.isReadable(path)) {
            ioManager.error("Access denied: $filename")
            return
        }

        recursionDepth++
        executedScripts.add(filename)
        try {
            processScriptFile(path)
        } catch (e: Exception) {
            ioManager.error("Script error: ${e.message}")
        } finally {
            executedScripts.remove(filename)
            recursionDepth--
        }
    }

    private fun processScriptFile(path: Path) {
        val scriptInput = object : InputManager {
            val reader = Files.newBufferedReader(path)
            override fun readLine(): String? = reader.readLine()
            override fun hasInput(): Boolean = reader.ready()
        }

        val scriptIO = IOManager(
            scriptInput,
            object : OutputManager {
                override fun write(text: String) = ioManager.outputInline(text)
                override fun writeLine(text: String) = ioManager.outputLine(text)
                override fun error(text: String) = ioManager.error("SCRIPT: $text")
            }
        )

        val originalIO = vehicleReader.getIOManager()
        vehicleReader.setIOManager(scriptIO)

        try {
            while (scriptInput.hasInput()) {
                val line = scriptIO.readLine().trim()
                if (line.isNotEmpty()) {
                    ioManager.outputLine("[Script]> $line")
                    processCommand(line)
                }
            }
        } finally {
            vehicleReader.setIOManager(originalIO)
            (scriptInput as? AutoCloseable)?.close()
        }
    }
}