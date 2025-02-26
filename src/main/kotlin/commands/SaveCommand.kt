package org.example.commands

import org.example.core.CollectionManager

class SaveCommand : Command (
    name = "save",
    description = "Save the collection to a file."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {

    }
}