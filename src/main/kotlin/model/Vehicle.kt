package org.example.model
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Vehicle(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    @Contextual val creationDate: Date,
    val enginePower: Double,
    val distanceTravelled: Double?,
    val type: VehicleType?,
    val fuelType: FuelType?
) {
    init {
        require(id > 0) { "ID must be >0" }
        require(name.isNotEmpty()) { "Name can't be empty" }
        require(enginePower > 0) { "Engine power must >0" }
        distanceTravelled?.let { require(it > 0) { "Distance must be >0" } }
    }
}