package org.example.commands

import org.example.core.CollectionManager

class SaveCommand : Command (
    name = "save",
    description = "Save the collection to a file.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${args.size}.")
            return
        }
        collectionManager.saveToFile()
        println("Data saved to your file.")
    }
}