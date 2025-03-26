package org.example.commands

import org.example.core.CollectionManager

class RemoveFirstCommand : Command (
    name = "remove_first",
    description = "Delete a first item in collection.",
    size = 0
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${size}.")
            return
        }
        if(collectionManager.isEmpty()) {
            println("No element in the collection.")
            return
        }
        collectionManager.deleteByNumber(0)
        println("First element removed.")
    }

}