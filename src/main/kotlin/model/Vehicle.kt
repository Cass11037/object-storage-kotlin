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
) : Comparable<Vehicle> {
    init {
        require(id >= 0) { "ID must be positive" }
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(enginePower > 0) { "Engine power must be positive" }
        distanceTravelled?.let { require(it > 0) { "Distance must be positive if provided" } }
    }
    override fun compareTo(other: Vehicle): Int {
        return this.enginePower.compareTo(other.enginePower)
    }
    override fun toString(): String {
        return "Vehicle(id=$id, name='$name', coordinates=$coordinates, creationDate=$creationDate, " +
                "enginePower=$enginePower, distanceTravelled=$distanceTravelled, type=$type, fuelType=$fuelType)"
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // Проверка на ссылочное равенство
        if (other !is Vehicle) return false // Проверка на тип
        return this.id == other.id // Сравнение по id
    }
    override fun hashCode(): Int {
        return id.hashCode() // Хэш-код на основе id
    }
}