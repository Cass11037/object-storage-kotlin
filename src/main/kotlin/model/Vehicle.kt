package org.example.model
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Vehicle(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    // Как то сериализировать надо val creationDate: Date,
    val enginePower: Double,
    val distanceTravelled: Double?,
    val type: VehicleType?,
    val fuelType: FuelType?
) {
    init {
        require(id > 0) { "ID must be positive" }
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(enginePower > 0) { "Engine power must be positive" }
        distanceTravelled?.let { require(it > 0) { "Distance must be positive if provided" } }
    }
}