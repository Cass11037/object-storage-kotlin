package org.example.commands

import IOManager
import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddIfMaxCommand (  private val reader: VehicleReader) : Command (
    name = "add_if_max",
    description = "Add a new item to a collection if its value exceeds the value of the largest item in that collection.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        val newVehicle = reader.readVehicle()
        val maxVehicle = collectionManager.getMax()
        if(maxVehicle == null || maxVehicle < newVehicle) {
            collectionManager.addVehicle(newVehicle)
            ioManager.outputLine("Vehicle added with ID: ${newVehicle.id}")
        } else {
            ioManager.outputLine("New vehicle\'s value doesn\'t exceed the value of the largest item in that collection.")
        }
    }
}