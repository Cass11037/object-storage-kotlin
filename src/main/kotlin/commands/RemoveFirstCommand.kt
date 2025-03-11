package org.example.commands

import org.example.core.CollectionManager

class RemoveFirstCommand : Command (
    name = "remove_first",
    description = "Delete a first item in collection."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(collectionManager.isEmpty()) {
            println("No element in the collection.")
            return
        }
        collectionManager.deleteByNumber(0)
        println("First element removed.")
    }

}