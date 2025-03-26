package org.example.commands

import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddCommand(
    private val reader: VehicleReader
) : Command("add", "Add new vehicle to collection", 0) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${size}.")
            return
        }
        val vehicle = collectionManager.addVehicle(reader.readVehicle())
        println("Vehicle added with ID: ${vehicle.id}")
    }
}