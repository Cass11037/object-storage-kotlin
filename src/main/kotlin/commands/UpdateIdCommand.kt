package org.example.commands

import IOManager
import org.example.core.CollectionManager
import org.example.core.VehicleReader
import org.example.model.Vehicle


class UpdateIdCommand (
    private val reader: VehicleReader
) : Command (
    name = "update_id",
    description = "Update the element value by id.",
    size = 1
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        val id = args[0].toInt()
        val vehicle: Vehicle? = collectionManager.getById(id)
        if (vehicle == null) {
            ioManager.outputLine("Can not find vehicle by $id. Your collection\' max id = ${collectionManager.size() - 1}.")
            return
        }
        reader.readUpdatesForVehicle(vehicle)
        ioManager.outputLine("Vehicle $id was updated.")
    }
}
