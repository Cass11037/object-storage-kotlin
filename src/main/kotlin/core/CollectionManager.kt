package org.example.core

import org.apache.commons.csv.*
import org.example.model.*
import java.io.File
import java.io.FileWriter
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
            val file = File(filename)
            CSVParser(file.bufferedReader(), CSVFormat.DEFAULT.withHeader()).use { parser ->
                for ((index, record) in parser.withIndex()) {
                    try {
                        val id = requireNotNull(record["id"]) { "ID отсутствует" }.toInt()
                        val name = requireNotNull(record["name"]) { "Имя отсутствует" }
                        val x = requireNotNull(record["coordinatesX"]) { "Координата X отсутствует" }.toInt()
                        val y = requireNotNull(record["coordinatesY"]) { "Координата Y отсутствует" }.toFloat()
                        val creationDate =
                            requireNotNull(record["creationDate"]) { "Дата создания отсутствует" }.toLong()
                        val enginePower =
                            requireNotNull(record["enginePower"]) { "Мощность двигателя отсутствует" }.toDouble()

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
                        errors.add("Ошибка в строке ${index + 2}: ${e.message ?: "Неизвестная ошибка"}")
                    }
                }
            }

            if (errors.isNotEmpty()) errors else warnings
        } catch (e: Exception) {
            errors.add("Критическая ошибка: ${e.message}")
            errors
        }
    }

    // Метод валидации
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
        if (name.isBlank()) warnings.add("Ошибка в ID $id: Пустое имя")
        if (x > 806) warnings.add("Ошибка в ID $id: Координата X > 806")
        if (y > 922) warnings.add("Ошибка в ID $id: Координата Y > 922")
        if (enginePower <= 0) warnings.add("Ошибка в ID $id: Мощность двигателя должна быть положительной")
        distanceTravelled?.let {
            if (it < 0) warnings.add("Ошибка в ID $id: Пройденное расстояние должно быть положительным")
        }
        if (type == null) warnings.add("Предупреждение в ID $id: Тип ТС не указан")
        if (fuelType == null) warnings.add("Предупреждение в ID $id: Тип топлива не указан")
    }

    fun saveToFile() {
        try {
            val writer = FileWriter(filename)
            CSVPrinter(writer, CSVFormat.DEFAULT).use { printer ->
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
                // Проверка и запись данных
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
        } catch (e: Exception) {
            println("Ошибка сохранения данных: ${e.message}")
            e.printStackTrace()
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

    fun getAll() = vehicles.toList()

    //TODO removeById
    fun clear() {
        vehicles.clear()
        lastId = 0
        saveToFile()
    }

    fun getById(id: Int): Vehicle {
        return vehicles[id]
    }
}
