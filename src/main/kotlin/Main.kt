package org.example

import org.example.commands.*
import org.example.core.CollectionManager
import org.example.core.CommandProcessor
import org.example.core.VehicleReader
import java.util.*


fun main() {
    val collectionManager = CollectionManager("data.json")
    val scanner = Scanner(System.`in`)
    val vehicleReader = VehicleReader(scanner)

    val commands = listOf(
        HelpCommand(emptyMap()),
        AddCommand(vehicleReader),
        ClearCommand(),
        ShowCommand(),
        InfoCommand(vehicleReader),

    ).associateBy { it.getName() }
    CommandProcessor(commands, scanner, collectionManager).start()
    collectionManager.saveToFile()
    println("Data saved to data.json")
}