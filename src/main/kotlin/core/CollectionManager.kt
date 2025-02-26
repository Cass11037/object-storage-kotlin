package org.example.core

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.model.Vehicle
import java.io.File
import java.util.LinkedList

class CollectionManager(private val filename: String) {
    private val vehicles : MutableList<Vehicle> = LinkedList()
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
        vehicles.add(vehicle.copy(id = ++lastId, creationDate = 199191919))//TODO creation date
    }

    fun getAll() = vehicles.toList()


    //TODO removeById
    fun clear() {
        vehicles.clear()
        lastId = 0
        saveToFile()
    }
    fun getById (id : Int) : Vehicle{
        return vehicles[id]
    }
    //fun deleteById(i)
}