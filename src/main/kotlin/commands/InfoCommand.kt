package org.example.commands


import org.example.core.CollectionManager
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InfoCommand :Command(
    name = "info",
    description = "Displays project information.",
    size = 0
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${size}.")
            return
        }
        println("Info:")
        println("Collection type: ${collectionManager.getAll()::class.simpleName}")
        println("Amount of elements: ${collectionManager.size()}")
        if (collectionManager.getAll().isNotEmpty()) {
            val readableDate = Instant.ofEpochMilli(collectionManager.getAll().first().creationDate)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            println("Creation date: $readableDate")
        } else {
            println("Collection is empty.")
        }
    }
}