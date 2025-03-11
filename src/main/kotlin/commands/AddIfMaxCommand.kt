package org.example.commands

import org.example.core.CollectionManager
import org.example.core.VehicleReader
import org.example.model.Vehicle
import kotlin.math.max

class AddIfMaxCommand (  private val reader: VehicleReader) : Command (
    name = "add_if_max",
    description = "Add a new item to a collection if its value exceeds the value of the largest item in that collection."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        var newVehicle = reader.readVehicle()
        var maxVehicle = collectionManager.getMax()
        if(maxVehicle == null || maxVehicle < newVehicle) {
            collectionManager.addVehicle(newVehicle)
            println("Vehicle added with ID: ${newVehicle.id}")
        } else {
            println("New vehicle\'s value doesn\'t exceed the value of the largest item in that collection.")
        }
    }
}