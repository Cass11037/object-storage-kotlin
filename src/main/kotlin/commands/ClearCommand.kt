package org.example.commands

import IOManager
import org.example.core.CollectionManager

class ClearCommand : Command (
    name = "clear",
    description = "Clear collection.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        collectionManager.clear()
        println("Collection is clear.")
    }
}
