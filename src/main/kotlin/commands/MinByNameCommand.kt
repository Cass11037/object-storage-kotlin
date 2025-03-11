package org.example.commands

import org.example.core.CollectionManager

class MinByNameCommand : Command (
    name = "min_by_name",
    description = "Find item with minimal name."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        TODO("Not yet implemented")
    }
}