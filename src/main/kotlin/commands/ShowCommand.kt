package org.example.commands

import org.example.core.CollectionManager

class ShowCommand : Command(
    name = "show",
    description = "Display all the items in the collection.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${size}.")
            return
        }
        if(collectionManager.isEmpty()) {
            println("Collection is empty.")
        } else {
            collectionManager.getAll().forEach {
                vehicle -> println(vehicle)
            }
        }
    }
}