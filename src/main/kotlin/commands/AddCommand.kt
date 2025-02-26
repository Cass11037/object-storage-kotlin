package org.example.commands


import org.example.core.CollectionManager
import org.example.core.VehicleReader

class AddCommand(
    collection: CollectionManager,
    private val reader: VehicleReader
) : BaseCommand("add", "Add new vehicle to collection", collection) {
    override fun execute(args: List<String>) {
        val vehicle = reader.readVehicle()
        collection.addVehicle(vehicle)
        println("Vehicle added with ID: ${vehicle.id}")
    }
}