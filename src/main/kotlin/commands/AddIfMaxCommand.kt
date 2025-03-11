package org.example.commands

import org.example.core.CollectionManager

class AddIfMaxCommand : Command (
    name = "add_if_max",
    description = "Add a new item to a collection if its value exceeds the value of the largest item in that collection."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        TODO("Not yet implemented")
    }
}