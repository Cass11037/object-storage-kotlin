package org.example.core

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.model.Vehicle
import java.io.File

class CollectionManager(private val filename: String) {
    private val vehicles = mutableListOf<Vehicle>()
    private var lastId = 0

    init {
        loadFromFile()
        lastId = vehicles.maxOfOrNull { it.id } ?: 0
    }

    private fun loadFromFile() {
        try {
            val file = File(filename)
            if (file.exists()) {
                vehicles.addAll(Json.decodeFromString<List<Vehicle>>(file.readText()))
            }
        } catch (e: Exception) {
            println("Error loading data: ${e.message}")
        }
    }

    fun saveToFile() {
        File(filename).writeText(Json.encodeToString(vehicles))
    }

    fun addVehicle(vehicle: Vehicle) {
        vehicles.add(vehicle.copy(id = ++lastId, creationDate = 199191919))
    }

    fun getAll() = vehicles.toList()
    //TODO removeById
    fun clear() {
        vehicles.clear()
        lastId = 0
    }
    fun getById (id : Int) : Vehicle{
        return vehicles[id]
    }



//    Обработку остальных команд (удаление, обновление)
//    Валидацию входных данных
//    Пагинацию при выводе
//    Логирование операций
}