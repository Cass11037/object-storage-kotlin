package org.example.commands

import IOManager
import org.example.core.CollectionManager

abstract class FilterByCharacteristicCommand (
    name: String,
    description: String,
    size: Int) : Command (
    name = name,
    description = description,
    size = size

) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        val vehicles = collectionManager.filterByCharacteristic(args[0], args[1])
        if(vehicles.isEmpty())  {
            ioManager.outputLine("No vehicles found with $args[0] = $args[1]")
            return
        }
        ioManager.outputLine("Vehicles with $args[0] = $args[1]:")
        ioManager.outputLine(vehicles)
    }
}