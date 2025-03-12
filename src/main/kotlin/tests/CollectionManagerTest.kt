package org.example.tests

import org.example.core.CollectionManager
import org.example.model.Coordinates
import org.example.model.Vehicle
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CollectionManagerTest {

    @Test
    fun addVehicle() {
        val collectionManager = CollectionManager("test.csv")

        // Создаем тестовый объект Vehicle
        val vehicle = Vehicle(
            id = 1,
            name = "Tesla",
            coordinates = Coordinates(1, 1.0f),
            creationDate = System.currentTimeMillis(),
            enginePower = 10.2,
            distanceTravelled = null,
            type = null,
            fuelType = null
        )

        // Добавляем объект в коллекцию
        collectionManager.addVehicle(vehicle)

        // Проверяем, что объект был добавлен
        assertEquals(1, collectionManager.getAll().size)
        assertEquals(1, collectionManager.getAll()[0].id)
    }
}