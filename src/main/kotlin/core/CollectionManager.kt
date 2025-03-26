package org.example.core

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord
import org.example.model.Coordinates
import org.example.model.FuelType
import org.example.model.Vehicle
import org.example.model.VehicleType
import java.io.*
import java.util.*

@Suppress("DEPRECATION")
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
        val requiredHeaders = listOf(
            "id", "name", "coordinatesX", "coordinatesY",
            "creationDate", "enginePower", "distanceTravelled", "type", "fuelType"
        )
        val file = File(filename)
        val uniqueIds = mutableSetOf<Int>()

        try {
            if (!file.exists()) {
                createFileWithHeaders(file, requiredHeaders)
                warnings.add("File created with headers as it didn't exist")
                return warnings
            }

            if (file.length() == 0L) {
                createFileWithHeaders(file, requiredHeaders)
                warnings.add("File was created with headers")
                return warnings
            }

            if (!validateHeaders(file, requiredHeaders)) {
                backupAndFixFile(file, requiredHeaders)
                warnings.add("Invalid headers. File was backed up and emptied")
                return warnings
            }

            parseData(file, uniqueIds, warnings, errors)
            return if (errors.isEmpty()) warnings else errors
        } catch (e: Exception) {
            return listOf("Critical error: ${e.message ?: "Unknown error"}")
        }
    }

    //Создать файл и заполнить его только заголовками
    private fun createFileWithHeaders(file: File, headers: List<String>) {
        CSVPrinter(FileWriter(file), CSVFormat.DEFAULT).use {
            it.printRecord(headers)
        }
    }

    //Проверка заголовков
    private fun validateHeaders(file: File, expected: List<String>): Boolean {
        BufferedReader(FileReader(file)).use {
            val actual = it.readLine().split(",")
            return actual == expected
        }
    }

    //Сохраняем файл с .bak и вызываем createFileWithHeaders
    private fun backupAndFixFile(original: File, headers: List<String>) {
        val timestamp = System.currentTimeMillis()
        val backup = File("${original.path}.bak_$timestamp")
        original.copyTo(backup, overwrite = true)
        createFileWithHeaders(original, headers)
    }

    //Построково проверяем данные и добавляем их
    private fun parseData(
        file: File,
        uniqueIds: MutableSet<Int>,
        warnings: MutableList<String>,
        errors: MutableList<String>
    ) {
        var lineNumber = 1

        CSVParser(FileReader(file), CSVFormat.DEFAULT.withHeader()).use { parser ->
            for (record in parser) {
                lineNumber++
                try {
                    val id = parseId(record, uniqueIds)
                    val name = parseName(record)
                    val coordinates = parseCoordinates(record, id, warnings)
                    val creationDate = parseCreationDate(record)
                    val enginePower = parseEnginePower(record)
                    val distanceTravelled = parseDistance(record, id, warnings)
                    val (type, fuelType) = parseEnums(record)

                    val vehicle = Vehicle(
                        id,
                        name,
                        coordinates,
                        creationDate,
                        enginePower,
                        distanceTravelled,
                        type,
                        fuelType
                    )

                    validateVehicle(vehicle, warnings)
                    vehicles.add(vehicle)
                } catch (e: Exception) {
                    errors.add("Line $lineNumber: ${e.message}")
                }
            }
        }
    }

    private fun parseId(record: CSVRecord, uniqueIds: MutableSet<Int>): Int {
        val id = record["id"]?.toIntOrNull()
            ?: throw Exception("Missing ID")

        when {
            id < 0 -> throw Exception("Negative ID")
            !uniqueIds.add(id) -> throw Exception("Duplicate ID $id")
        }
        return id
    }

    private fun parseName(record: CSVRecord): String {
        //Возвращает имя или null
        return record["name"]?.takeIf { it.isNotBlank() }
            ?: throw Exception("Missing name")
    }

    private fun parseCoordinates(record: CSVRecord, id: Int, warnings: MutableList<String>): Coordinates {
        return try {
            val x = record["coordinatesX"]?.toIntOrNull()
                ?: throw Exception("Invalid X coordinate")
            val y = record["coordinatesY"]?.toFloatOrNull()
                ?: throw Exception("Invalid Y coordinate")

            val coordinates = Coordinates(x, y)
            if (x > 806) warnings.add("ID $id: X coordinate at maximum (806)")
            if (y > 922) warnings.add("ID $id: Y coordinate at maximum (922)")
            coordinates
        } catch (e: Exception) {
            warnings.add("ID $id: ${e.message}")
            throw Exception("Invalid coordinates format").initCause(e)
        }
    }

    private fun parseCreationDate(record: CSVRecord): Long {
        val date = record["creationDate"]?.toLongOrNull()
            ?: throw Exception("Invalid creation date")
        if (date > System.currentTimeMillis()) {
            throw Exception("Creation date is in the future")
        }
        return date
    }

    private fun parseEnginePower(record: CSVRecord): Double {
        return record["enginePower"]?.toDoubleOrNull()
            ?.takeIf { it > 0 }
            ?: throw Exception("Invalid engine power")
    }

    private fun parseDistance(record: CSVRecord, id: Int, warnings: MutableList<String>): Double? {
        return record["distanceTravelled"]?.takeIf { it.isNotBlank() }
            ?.toDoubleOrNull()
            ?.also {
                if (it <= 0) warnings.add("ID $id: Non-positive distance")
            }
    }

    private fun parseEnums(record: CSVRecord): Pair<VehicleType?, FuelType?> {
        val type = record["type"]?.toEnumOrNull<VehicleType>()
        val fuelType = record["fuelType"]?.toEnumOrNull<FuelType>()
        return type to fuelType
    }


    private fun validateVehicle(vehicle: Vehicle, warnings: MutableList<String>) {
        with(vehicle) {
            if (name.isBlank()) warnings.add("ID $id: Empty name")
            if (enginePower <= 0) warnings.add("ID $id: Engine power must be positive")
            distanceTravelled?.let {
                if (it <= 0) warnings.add("ID $id: Distance must be positive")
            }
        }
    }

    private inline fun <reified T : Enum<T>> String?.toEnumOrNull(): T? = this?.let { value ->
        enumValues<T>().firstOrNull { it.name.equals(value, ignoreCase = true) }
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
    fun saveToFile(file: String): List<String> {
        return try {
            fun createFile(fileName: String): String? {
                return try {
                    val file1= File(fileName)
                    when {
                        file1.exists() -> {
                            println("File '$fileName' already exists.")
                            fileName
                        }
                        file1.createNewFile() -> {
                            println("File '$fileName' created successfully.")
                            fileName
                        }
                        else -> {
                            println("Failed to create '$fileName'.")
                            null
                        }
                    }
                } catch (e: SecurityException) {
                    println("Security error: ${e.message}")
                    null
                } catch (e: IOException) {
                    println("IO error: ${e.message}")
                    null
                }
            }
            val newFile = createFile(file) ?: return emptyList()
            FileOutputStream(newFile).use { fileOutputStream ->
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
    fun addVehicle(newVehicle: Vehicle): Vehicle {
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
        return vehicles.last()
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
        if (this.isEmpty() || this.size() - 1 < number) {
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

        return if (this.isEmpty()) {
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
        return if (this.isEmpty()) {
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

    fun findByCharacteristic(characteristic: String, arg: String): Vehicle? {
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