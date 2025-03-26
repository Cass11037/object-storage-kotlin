package org.example.commands

import IOManager
import org.example.core.CollectionManager

class ShowCommand : Command(
    name = "show",
    description = "Display all the items in the collection.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        if(collectionManager.isEmpty()) {
            ioManager.outputLine("Collection is empty.")
        } else {
            collectionManager.getAll().forEach {
                vehicle -> ioManager.outputLine(vehicle)
            }
        }
    }
}