package org.example.commands

import org.example.core.CollectionManager

abstract class FilterByCharacteristicCommand (
    name: String,
    description: String,
    size: Int) : Command (
    name = name,
    description = description,
        size = size
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if (args.isEmpty() || args.size != 2) {
            println("Error: Args can not be empty.")
            return
        }
        val vehicles = collectionManager.filterByCharacteristic(args[0], args[1])
        if(vehicles.isEmpty())  {
            println("No vehicles found with $args[0] = $args[1]")
            return
        }
        println("Vehicles with $args[0] = $args[1]:")
        println(vehicles)
    }
}