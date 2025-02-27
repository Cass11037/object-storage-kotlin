package org.example.core

import org.example.commands.*
import java.io.File
import java.util.Scanner

class CommandProcessor(
    private val commands: Map<String, Command>,
    private val scanner: Scanner,
    private var collectionManager: CollectionManager,
) {
    fun start() {

        println("Transport manager 3000")
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
            command.execute(parts.drop(1), collectionManager)
        } catch (e: Exception) {
            println("Error executing command: ${e.message}")
        }
    }

    private fun printHelp() {
        val help = HelpCommand(commands)
        help.execute(emptyList(), collectionManager)
    }
}