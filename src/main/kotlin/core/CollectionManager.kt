package org.example.core

import org.apache.commons.csv.*
import org.example.model.*
import java.io.*
import java.util.LinkedList

class CollectionManager(private val filename: String) {
    private val vehicles: MutableList<Vehicle> = LinkedList()
    private var lastId = 0

    init {
        loadFromFile()
        lastId = vehicles.maxOfOrNull { it.id } ?: 0
    }
    private fun loadFromFile(): List<String> {
        val warnings = mutableListOf<String>()
        val errors = mutableListOf<String>()

        return try {
            FileReader(filename).use { fileReader ->
                CSVParser(fileReader, CSVFormat.DEFAULT.withHeader()).use { parser ->
                    vehicles.clear()
                    for ((index, record) in parser.withIndex()) {
                        try {
                            val id = requireNotNull(record["id"]) { "ID missing" }.toInt()
                            val name = requireNotNull(record["name"]) { "Name missing" }
                            val x = requireNotNull(record["coordinatesX"]) { "X coordinate missing" }.toInt()
                            val y = requireNotNull(record["coordinatesY"]) { "XY coordinate missing" }.toFloat()
                            val creationDate =
                                requireNotNull(record["creationDate"]) { "Date of creation missing" }.toLong()
                            val enginePower =
                                requireNotNull(record["enginePower"]) { "Engine power missing" }.toDouble()

                            val distanceTravelled = record["distanceTravelled"]?.takeIf { it.isNotBlank() }?.toDouble()
                            val type = record["type"]?.takeIf { it.isNotBlank() }?.let { VehicleType.valueOf(it) }
                            val fuelType = record["fuelType"]?.takeIf { it.isNotBlank() }?.let { FuelType.valueOf(it) }

                            validateVehicle(id, name, x, y, enginePower, distanceTravelled, type, fuelType, warnings)

                            vehicles.add(
                                Vehicle(
                                    id,
                                    name,
                                    Coordinates(x, y),
                                    creationDate,
                                    enginePower,
                                    distanceTravelled,
                                    type,
                                    fuelType
                                )
                            )
                        } catch (e: Exception) {
                            errors.add("Error at ${index + 2}: ${e.message ?: "Unknown error"}")
                        }
                    }
                }
            }
            errors.ifEmpty { warnings }
        } catch (e: Exception) {
            errors.add("Critical error: ${e.message}")
            errors
        }
    }

    private fun validateVehicle(
        id: Int,
        name: String,
        x: Int,
        y: Float,
        enginePower: Double,
        distanceTravelled: Double?,
        type: VehicleType?,
        fuelType: FuelType?,
        warnings: MutableList<String>
    ) {
        if (name.isBlank()) warnings.add("Error at ID $id: Empty name")
        if (x > 806) warnings.add("Error at ID $id: Coordinate X > 806")
        if (y > 922) warnings.add("Error at ID $id: Coordinate Y > 922")
        if (enginePower <= 0) warnings.add("Error at ID $id: Engine power must be positive")
        distanceTravelled?.let {
            if (it < 0) warnings.add("Error at ID $id: Distance travelled must be positive")
        }
        if (type == null) warnings.add("Warning at ID $id: Vehicle type missing")
        if (fuelType == null) warnings.add("Warning at ID $id: Fuel type missing")
    }

    fun saveToFile(): List<String> {
        return try {
            FileOutputStream(filename).use { fileOutputStream ->
                OutputStreamWriter(fileOutputStream, "UTF-8").use { writer ->
                    CSVPrinter(writer, CSVFormat.DEFAULT).use { printer ->
                        // Запись заголовков
                        printer.printRecord(
                            "id",
                            "name",
                            "coordinatesX",
                            "coordinatesY",
                            "creationDate",
                            "enginePower",
                            "distanceTravelled",
                            "type",
                            "fuelType"
                        )

                        // Запись данных
                        vehicles.forEach { vehicle ->
                            printer.printRecord(
                                vehicle.id,
                                vehicle.name,
                                vehicle.coordinates.x,
                                vehicle.coordinates.y,
                                vehicle.creationDate,
                                vehicle.enginePower,
                                vehicle.distanceTravelled,
                                vehicle.type?.name,
                                vehicle.fuelType?.name
                            )
                        }
                    }
                }
            }
            emptyList()
        } catch (e: IOException) {
            listOf("Error while saving file: ${e.message}")
        }
    }

    fun addVehicle(newVehicle: Vehicle) {
        val newId = ++lastId
        vehicles.add(
            Vehicle(
                newId,
                newVehicle.name,
                newVehicle.coordinates,
                System.currentTimeMillis(),
                newVehicle.enginePower,
                newVehicle.distanceTravelled,
                newVehicle.type,
                newVehicle.fuelType
            )
        )
    }

    fun getById(id: Int): Vehicle? {
        return vehicles.find { it.id == id }
    }
    fun deleteElement(id: Int) {
        val vehicleToRemove = vehicles.find { it.id == id }
        if (vehicleToRemove != null) {
            vehicles.remove(vehicleToRemove) // Удаляем найденный Vehicle
        }
    }
    fun deleteElement(vehicle: Vehicle) {
        if (vehicles.contains(vehicle)) {
            vehicles.remove(vehicle) // Удаляем найденный Vehicle
        }
    }
    fun deleteByNumber(number: Int) {
        if(this.isEmpty() || this.size() - 1 < number) {
            return
        } else {
            vehicles.removeAt(number)
        }
    }
    fun size(): Int {
        return vehicles.size
    }
    fun isEmpty(): Boolean {
        return vehicles.isEmpty()
    }

    fun clear() {
        vehicles.clear()
        lastId = 1
        VehicleReader.clearId()
    }
    fun getMax(): Vehicle? {
        return vehicles.maxOrNull()
    }
    fun getMin(): Vehicle? {
        return vehicles.minOrNull()
    }
    fun getMin(characteristic: String): Vehicle? {

        return if(this.isEmpty()) {
            null
        } else {
            when (characteristic) {
                "id" -> vehicles.minBy { it.id }
                "name" -> vehicles.minBy { it.name }
                "coordinates" -> vehicles.minBy { it.coordinates.toString() }
                "enginePower" -> vehicles.minBy { it.enginePower }
                "distanceTravelled" -> vehicles.minBy { it.distanceTravelled ?: Double.MAX_VALUE }
                "type" -> vehicles.minBy { it.type?.name ?: "" }
                "fuelType" -> vehicles.minBy { it.fuelType?.name ?: "" }
                else -> throw IllegalArgumentException("Unknown characteristic: $characteristic")
            }
        }

    }
    fun getMax(characteristic: String): Vehicle? {

        return if(this.isEmpty()) {
            null
        } else {
            when (characteristic) {
                "id" -> vehicles.maxBy { it.id }
                "name" -> vehicles.maxBy { it.name }
                "coordinates" -> vehicles.maxBy { it.coordinates.toString() }
                "enginePower" -> vehicles.maxBy { it.enginePower }
                "distanceTravelled" -> vehicles.maxBy { it.distanceTravelled ?: Double.MAX_VALUE }
                "type" -> vehicles.maxBy { it.type?.name ?: "" }
                "fuelType" -> vehicles.maxBy { it.fuelType?.name ?: "" }
                else -> throw IllegalArgumentException("Unknown characteristic: $characteristic")
            }
        }
    }
    fun findByCharacteristic(characteristic: String, arg: String) : Vehicle? {
        return when (characteristic) {
            "id" -> vehicles.find { it.id == arg.toInt() }
            "name" -> vehicles.find { it.name == arg }
            "coordinates" -> vehicles.find { it.coordinates.toString() == arg }
            "enginePower" -> vehicles.find { it.enginePower == arg.toDouble() }
            "distanceTravelled" -> vehicles.find { it.distanceTravelled == arg.toDoubleOrNull() }
            "type" -> vehicles.find { it.type?.name.equals(arg, ignoreCase = true) }
            "fuelType" -> vehicles.find { it.fuelType?.name.equals(arg, ignoreCase = true) }
            else -> throw IllegalArgumentException("Unknown characteristic: $characteristic")
        }
    }
    fun filterByCharacteristic(characteristic: String, arg: String): List<Vehicle> {
        return when (characteristic) {
            "id" -> vehicles.filter { it.id == arg.toInt() }
            "name" -> vehicles.filter { it.name == arg }
            "coordinates" -> vehicles.filter { it.coordinates.toString() == arg }
            "enginePower" -> vehicles.filter { it.enginePower == arg.toDouble() }
            "distanceTravelled" -> vehicles.filter { it.distanceTravelled == arg.toDoubleOrNull() }
            "type" -> vehicles.filter { it.type?.name.equals(arg, ignoreCase = true) }
            "fuelType" -> vehicles.filter { it.fuelType?.name.equals(arg, ignoreCase = true) }
            else -> throw IllegalArgumentException("Unknown characteristic: $characteristic")
        }
    }
    fun getAll() = vehicles.toList()
}