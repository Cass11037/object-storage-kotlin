package org.example.commands

import org.example.core.CollectionManager

class RemoveById : Command (
    name = "remove_by_id",
    description = "Delete an item from the collection by its id."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {

    }
}