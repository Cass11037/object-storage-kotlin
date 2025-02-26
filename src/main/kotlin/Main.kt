package org.example

import org.example.commands.AddCommand
import org.example.commands.ClearCommand
import org.example.commands.ShowCommand
import org.example.core.CollectionManager
import org.example.core.CommandProcessor
import org.example.core.VehicleReader
import java.util.*


fun main() {
    val collectionManager = CollectionManager("data.json")
    val scanner = Scanner(System.`in`)
    val vehicleReader = VehicleReader(scanner)

    val commands = listOf(
        AddCommand(vehicleReader),
        ClearCommand(),
        ShowCommand()
    ).associateBy { it.getName() }

    CommandProcessor(commands, scanner, collectionManager).start()
    collectionManager.saveToFile()
    println("Data saved to data.json")
}