package org.example.commands

import IOManager
import org.example.core.CollectionManager

class MinByNameCommand : MinByCharacteristicCommand (
    name = "min_by_name",
    description = "Find item with minimal name.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        super.execute(listOf("name"), collectionManager, ioManager)
    }
}