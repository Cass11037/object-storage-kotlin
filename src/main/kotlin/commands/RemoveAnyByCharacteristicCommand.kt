package org.example.commands

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
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        checkSizeOfArgs(args.size, 2)
        if (args.isEmpty() || args.size != 2) {
            println("Error: Args can not be empty.")
            return
        }
        val vehicle = collectionManager.findByCharacteristic(args[0], args[1])
        if(vehicle == null)  {
            println("No vehicle found with $args[0] = $args[1]")
            return
        }
        collectionManager.deleteElement(vehicle)
        println("Element removed: $vehicle")
    }
}