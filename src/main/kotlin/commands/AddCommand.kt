package org.example.commands

import IOManager
import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddCommand(
    private val reader: VehicleReader
) : Command("add", "Add new vehicle to collection", 0) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if (args.size == 7) {
            val vehicle = reader.readVehicleFromScript(args)
            collectionManager.addVehicle(vehicle)
            ioManager.outputLine("Vehicle added from script with ID: ${vehicle.id}")
        } else {
            val vehicle = reader.readVehicle()
            collectionManager.addVehicle(vehicle)
            ioManager.outputLine("Vehicle added interactively with ID: ${vehicle.id}")
        }
    }
}