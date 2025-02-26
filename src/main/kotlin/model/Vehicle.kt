package org.example.model
import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id: Int,
    var name: String,
    var coordinates: Coordinates,
    val creationDate: Long,
    var enginePower: Double,
    var distanceTravelled: Double?,
    var type: VehicleType?,
    var fuelType: FuelType?
) {
    init {
        require(id > 0) { "ID must be positive" }
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(enginePower > 0) { "Engine power must be positive" }
        distanceTravelled?.let { require(it > 0) { "Distance must be positive if provided" } }
    }
}