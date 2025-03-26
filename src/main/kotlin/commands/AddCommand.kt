package org.example.commands

import IOManager
import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddCommand(
    private val reader: VehicleReader
) : Command("add", "Add new vehicle to collection", 0) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        val vehicle = collectionManager.addVehicle(reader.readVehicle())
        ioManager.outputLine("Vehicle added with ID: ${vehicle.id}")
    }
}