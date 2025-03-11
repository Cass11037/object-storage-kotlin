package org.example.commands

import org.example.core.CollectionManager

class RemoveAnyByCharacteristicCommand (
) : Command(
    name = "remove_any_by_engine_power",
    description = "Remove an item by engine power."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if (args.isEmpty() || args.size != 2) {
            println("Error: Args can not be empty.")
            return
        }
        1val vehicle = collectionManager.findByCharacteristic(args[0], args[1])
        if(vehicle == null)  {
            println("No vehicle found with $args[0] = $args[1]")
            return
        }
        collectionManager.deleteElement(vehicle)
        println("Element removed: $vehicle")
    }
}