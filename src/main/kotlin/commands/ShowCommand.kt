package org.example.commands

import org.example.core.CollectionManager

class ShowCommand : Command(
    name = "show",
    description = "Display all the items in the collection."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(collectionManager.getAll().isEmpty()) {
            println("Collection is empty.")
        } else {
            collectionManager.getAll().forEach {
                vehicle -> println(vehicle)
            }
        }
    }
}