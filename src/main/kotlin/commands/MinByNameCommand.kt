package org.example.commands

import org.example.core.CollectionManager

class MinByNameCommand : MinByCharacteristicCommand (
    name = "min_by_name",
    description = "Find item with minimal name.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${args.size}.")
            return
        }
        super.execute(listOf("name"), collectionManager)
    }
}