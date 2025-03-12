package org.example.commands

import org.example.core.CollectionManager

class RemoveByIdCommand : Command (
    name = "remove_by_id",
    description = "Delete an item from the collection by its id.",
    size = 1
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${args.size}.")
            return
        }
        val id = args[0].toInt()
        if(collectionManager.getById(id) == null) {
            println("No element with $id.")
            return
        }
        collectionManager.deleteElement(id)
        println("Element with $id removed.")
    }
}