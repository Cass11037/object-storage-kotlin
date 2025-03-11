package org.example.commands

import org.example.core.CollectionManager

class ExitCommand : Command (
    name = "exit",
    description = "Exits the program without saving."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
       //not exists, because it needs only dor commandCollection
    }
}