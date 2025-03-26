package org.example.commands

import IOManager
import org.example.core.CollectionManager

abstract class RemoveAnyByCharacteristicCommand(
    name: String,
    description: String,
    size: Int
) : Command(
    name = name,
    description = description,
    size = size
){
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if (args.isEmpty() || args.size != 2) {
            ioManager.outputLine("Error: Args can not be empty.")
            return
        }
        val vehicle = collectionManager.findByCharacteristic(args[0], args[1])
        if(vehicle == null)  {
            ioManager.outputLine("No vehicle found with $args[0] = $args[1]")
            return
        }
        collectionManager.deleteElement(vehicle)
        ioManager.outputLine("Element removed: $vehicle")
    }
}