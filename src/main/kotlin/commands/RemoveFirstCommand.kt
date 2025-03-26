package org.example.commands

import IOManager
import org.example.core.CollectionManager

class RemoveFirstCommand : Command (
    name = "remove_first",
    description = "Delete a first item in collection.",
    size = 0
){
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        if(collectionManager.isEmpty()) {
            ioManager.outputLine("No element in the collection.")
            return
        }
        collectionManager.deleteByNumber(0)
        ioManager.outputLine("First element removed.")
    }

}