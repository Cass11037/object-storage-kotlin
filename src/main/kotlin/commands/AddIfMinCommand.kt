package org.example.commands

import IOManager
import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddIfMinCommand (  private val reader: VehicleReader) : Command (
    name = "add_if_min",
    description = "Add a new item to the collection if its value is less than that of the smallest item in this collection.",
    size = 0
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        val newVehicle = reader.readVehicle()
        val minVehicle = collectionManager.getMin()
        if(minVehicle == null || minVehicle > newVehicle) {
            collectionManager.addVehicle(newVehicle)
            ioManager.outputLine("Vehicle added with ID: ${newVehicle.id}")
        } else {
            ioManager.outputLine("New vehicle\'s value is not less then the value of the smallest item in that collection.")
        }
    }
}