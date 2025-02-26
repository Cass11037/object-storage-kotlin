package org.example.commands

import BaseCommand
import Coordinates
import Vehicle

class UpdateIdCommand : BaseCommand (
    name = "update_id",
    description = "Update the element value by id."
) {
    override fun execute(args: List<String>) {
        var vehicle: Vehicle= collection.getById(args[0].toInt())
        var propertyToChange = args[1]
        when(propertyToChange) {
            // val id: Int,
            //    val name: String,
            //    val coordinates: Coordinates,
            //    val creationDate: Long,
            //    val enginePower: Double,
            //    val distanceTravelled: Double?,
            //    val type: VehicleType?,
            //    val fuelType: FuelType?
            "name" -> vehicle.name =  args[2]
            "coordinates" -> vehicle.coordinates = Coordinates(args[2].toInt(), args[3].toFloat())
            "enginePower" -> vehicle.enginePower = args[2].toDouble()
            "" -> vehicle.distanceTravelled = args[2].toDouble()
            "" -> vehicle.type = args[2]
        }
    }
}