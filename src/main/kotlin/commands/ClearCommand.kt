package org.example.commands

import org.example.core.CollectionManager

class ClearCommand : Command (
    name = "clear",
    description = "Clear collection.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        collectionManager.clear()
        println("Collection is clear.")
    }
}
