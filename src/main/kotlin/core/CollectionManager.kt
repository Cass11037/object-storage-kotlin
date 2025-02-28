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
    //TODO delete all about lastId and reading from the file
    private fun loadFromFile(): List<String> {
        val warnings = mutableListOf<String>()
        val errors = mutableListOf<String>()

        return try {
            FileReader(filename).use { fileReader ->
                CSVParser(fileReader, CSVFormat.DEFAULT.withHeader()).use { parser ->
                    vehicles.clear()
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
            }
            if (errors.isNotEmpty()) errors else warnings
        } catch (e: Exception) {
            errors.add("Критическая ошибка: ${e.message}")
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
            listOf("Ошибка записи файла: ${e.message}")
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
    fun delete(id: Int) {
        vehicles.removeAt(id)
    }
    fun size(): Int {
        return vehicles.size
    }

    fun clear() {
        vehicles.clear()
        lastId = 0
        VehicleReader.clearId()
        //saveToFile() должен очищаться не файл, а введенные пользователем данные?
    }

    fun getAll() = vehicles.toList()
}