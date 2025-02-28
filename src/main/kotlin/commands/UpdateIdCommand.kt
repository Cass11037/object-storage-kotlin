package org.example.commands

import org.example.core.CollectionManager
import org.example.model.Coordinates
import org.example.model.FuelType
import org.example.model.Vehicle
import org.example.model.VehicleType


class UpdateIdCommand : Command (
    name = "update_id",
    description = "Update the element value by id. You can change: name, coordinates, enginePower, distanceTravelled, type, fuelType. Example: >update 1 name Tesla"
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        val id = args[0].toInt()
        val vehicle: Vehicle? = collectionManager.getById(id)
        if (vehicle == null) {
            println("Can not find vehicle by {$id}. Your collection\' max id = ${collectionManager.size() - 1}.")
            return
        }
        val propertyToChange = args[1]
        when (propertyToChange) {
            // val id: Int,
            //    val name: String,
            //    val coordinates: Coordinates,
            //    val creationDate: Long,
            //    val enginePower: Double,
            //    val distanceTravelled: Double?,
            //    val type: VehicleType?,
            //    val fuelType: FuelType?
            "name" -> vehicle.name = args[2]
            "coordinates" -> vehicle.coordinates = Coordinates(args[2].toInt(), args[3].toFloat())
            "enginePower" -> vehicle.enginePower = args[2].toDouble()
            "distanceTravelled" -> vehicle.distanceTravelled = args[2].toDouble()
            "type" -> vehicle.type = VehicleType.fromString(args[2])
            "fuelType" -> vehicle.fuelType = FuelType.fromString(args[2])
        }
    }
}
