package org.example.core

import IOManager
import org.example.commands.*
import java.nio.file.Files
import java.nio.file.Paths


class CommandProcessor(
    private var commands: Map<String, Command>,
    private val ioManager: IOManager,
    fileName: String
) {
    private lateinit var vehicleReader: VehicleReader

    constructor(ioManager: IOManager, fileName: String) : this(emptyMap(), ioManager, fileName)

    val collectionManager = CollectionManager(fileName)
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
               // executeScriptRegex.matches(input) -> executeScript(input)
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
            //executeScript(parts[0])
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
        val filename = parts[1]
        if (filename in executedScripts) {
            ioManager.outputLine("Error: Recursion detected in script execution $filename.")
            return
        }

        val path = Paths.get(filename)
        if (!Files.exists(path)) {
            ioManager.outputLine("Error: The $filename file was not found.")
            return
        }

        if (!Files.isReadable(path)) {
            ioManager.outputLine("Error: No rights to read the file $filename.")
            return
        }
        executedScripts.add(filename)
        val scriptIOManager = IOManager(Files.newBufferedReader(path))
        val originalIOManager = vehicleReader.getIOManager()

        try {
            vehicleReader.setIOManager(scriptIOManager)
            while (scriptIOManager.hasNextLine()) {
                val commandLine = scriptIOManager.readLine().trim()
                if (commandLine.isNotEmpty()) {
                    ioManager.outputLine("> $commandLine")
                    processCommand(commandLine)
                }
            }
            ioManager.outputLine("File $filename was read.")
        } finally {
            vehicleReader.setIOManager(originalIOManager)
            executedScripts.remove(filename)
        }
    }
}