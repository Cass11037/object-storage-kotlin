package org.example.commands

import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddCommand(
    private val reader: VehicleReader
) : Command("add", "Add new vehicle to collection", 0) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${args.size}.")
            return
        }
        val vehicle = reader.readVehicle()
        collectionManager.addVehicle(vehicle)
        println("Vehicle added with ID: ${vehicle.id}")
    }
}