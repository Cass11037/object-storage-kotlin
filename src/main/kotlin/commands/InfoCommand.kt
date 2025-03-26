package org.example.commands


import IOManager
import org.example.core.CollectionManager
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InfoCommand :Command(
    name = "info",
    description = "Displays project information.",
    size = 0
){
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        ioManager.outputLine("Info:")
        ioManager.outputLine("Collection type: ${collectionManager.getAll()::class.simpleName}")
        ioManager.outputLine("Amount of elements: ${collectionManager.size()}")
        if (collectionManager.getAll().isNotEmpty()) {
            val readableDate = Instant.ofEpochMilli(collectionManager.getAll().first().creationDate)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ioManager.outputLine("Creation date: $readableDate")
        } else {
            ioManager.outputLine("Collection is empty.")
        }
    }
}