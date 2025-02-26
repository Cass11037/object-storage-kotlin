package org.example.commands

import BaseCommand

class ClearCommand : BaseCommand (
    name = "clear",
    description = "Clear collection."
) {
    override fun execute(args: List<String>) {
        collection.clear()
        println("Collection is clear.")
    }
}