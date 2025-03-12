package org.example.commands

import org.example.core.CollectionManager
import org.example.core.VehicleReader
import io.mockk.*

class AddCommand(
    private val reader: VehicleReader
) : Command("add", "Add new vehicle to collection") {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        val vehicle = reader.readVehicle()
        collectionManager.addVehicle(vehicle)
        println("Vehicle added with ID: ${vehicle.id}")
    }
}