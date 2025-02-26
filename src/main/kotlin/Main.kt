package org.example

import CollectionManager
import CommandProcessor
import VehicleReader
import commands.AddCommand
import java.util.*


fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: program <datafile.json>")
        return
    }

    val collectionManager = CollectionManager(args[0])
    val scanner = Scanner(System.`in`)
    val vehicleReader = VehicleReader(scanner)

    val commands = listOf(
        AddCommand(collectionManager, vehicleReader),
    ).associateBy { it.getName() }

    CommandProcessor(commands, scanner).start()
    collectionManager.saveToFile()
    println("Data saved to ${args[0]}")
}