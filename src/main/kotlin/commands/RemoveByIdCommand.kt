package org.example.commands

import org.example.core.CollectionManager

class RemoveByIdCommand : RemoveAnyByCharacteristicCommand (
    name = "remove_by_id",
    description = "Delete an item from the collection by its id.",
    size = 1
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${args.size}.")
            return
        }
        //val id = args[0].toInt()
        /*if (args.isEmpty()) {
            println("No element with $id.")
            return
        }*/
        super.execute(listOf("id", args[0]), collectionManager)
    }
}