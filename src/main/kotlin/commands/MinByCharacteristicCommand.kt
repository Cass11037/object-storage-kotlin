package org.example.commands

import IOManager
import org.example.core.CollectionManager

abstract class MinByCharacteristicCommand (
    name: String,
    description: String,
    size: Int
) : Command(
    name = name,
    description = description,

    size = size
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        val vehicle = collectionManager.getMin(args[0])
        if(vehicle == null)  {
            ioManager.outputLine("No vehicle found.")
            return
        }
        ioManager.outputLine("Element found with minimal $args[0]: $vehicle")
    }
}