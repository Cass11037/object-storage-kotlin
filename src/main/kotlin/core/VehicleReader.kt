package org.example.core

import org.example.model.*
import java.util.Scanner

class VehicleReader(private val scanner: Scanner) {
    private val validInputs = listOf("name", "coordinates", "enginePower", "distanceTravelled", "type", "fuelType")
    companion object {
        private var nextId = 1 // Общая переменная для всех экземпляров
        fun clearId () {
            nextId = 1
        }
    }
    fun readUpdatesForVehicle(vehicle: Vehicle) {
        println("You can change: ${validInputs.joinToString(", ")}." )
        println("What do you want to change? > ")
        val input = scanner.nextLine()
        if (input in validInputs) {
            try {
                when (input) {
                    "name" -> vehicle.name = readNonEmptyString("Vehicle name")
                    "coordinates" -> vehicle.coordinates = readCoordinates()
                    "enginePower" -> vehicle.enginePower = readPositiveDouble("Engine power")
                    "distanceTravelled" -> vehicle.distanceTravelled = readOptionalDouble("Distance travelled")
                    "type" -> vehicle.type = readEnum("Vehicle type", VehicleType::class.java)
                    "fuelType" -> readEnum("Fuel type", FuelType::class.java)
                }
            } catch (e: Exception) {
                println("Input error: ${e.message}. Please try again.")
            }
        } else {
            println("Wrong input. Please enter one of these commands: ${validInputs.joinToString(", ")}.")
        }
    }
    fun readVehicle(): Vehicle {
        return Vehicle(
            id = nextId++,  // Временное значение
            name = readNonEmptyString("Vehicle name"),
            coordinates = readCoordinates(),
            creationDate = System.currentTimeMillis(),
            enginePower = readPositiveDouble("Engine power"),
            distanceTravelled = readOptionalDouble("Distance travelled"),
            type = readEnum("Vehicle type", VehicleType::class.java),
            fuelType = readEnum("Fuel type", FuelType::class.java)
        )
    }

    private fun readCoordinates(): Coordinates {
        return Coordinates(
            x = readBoundedInt("Coordinate X", max = 806),
            y = readBoundedFloat("Coordinate Y", max = 922f)
        )
    }
    private fun readNonEmptyString(prompt: String): String {
        while (true) {
            print("$prompt: ")
            val input = scanner.nextLine().trim()
            if (input.isNotEmpty()) return input
            println("Field cannot be empty!")
        }
    }

    private fun readBoundedInt(prompt: String, min: Int = Int.MIN_VALUE, max: Int): Int {
        while (true) {
            print("$prompt (<$max): ")
            val input = scanner.nextLine()
            try {
                val value = input.toInt()
                if (value in min..max) return value
                println("Value must be in range $min to $max")
            } catch (e: NumberFormatException) {
                println("Wrong INT!")
            }
        }
    }
    private fun readBoundedFloat(prompt: String,min: Float = Float.MIN_VALUE, max: Float): Float {
        while (true) {
            print("$prompt (max. $max): ")
            val input = scanner.nextLine()
            try {
                val value = input.toFloat()
                if (value in min.. max) return value
                println("Value must be in range 0.0000000000000000000000000000000000000000000014 to $max")
            } catch (e: NumberFormatException) {
                println("Incorrect value!")
            }
        }
    }
    private fun readPositiveDouble(prompt: String): Double {
        while (true) {
            print("$prompt: ")
            val input = scanner.nextLine()
            try {
                val value = input.toDouble()
                if (value > 0) return value
                println("Value must not be 0")
            } catch (e: NumberFormatException) {
                println("Incorrect value!")
            }
        }
    }
    private fun readOptionalDouble(prompt: String): Double? {
        print("$prompt (leave empty if no value): ")
        val input = scanner.nextLine().trim()
        return if (input.isEmpty()) {
            null
        } else {
            try {
                input.toDouble().takeIf { it > 0 }
                    ?: throw IllegalArgumentException("Value must be positive")
            } catch (e: NumberFormatException) {
                println("Wrong value! Filed will equal null")
                null
            }
        }
    }
    private inline fun <reified T : Enum<T>> readEnum(prompt: String, enumClass: Class<T>): T? {
        val values = enumClass.enumConstants.joinToString { it.name }
        println("$prompt (available values: $values)")
        while (true) {
            print("Input value (leave empty to cancel): ")
            val input = scanner.nextLine().trim()
            if (input.isEmpty()) return null
            try {
                return enumClass.enumConstants.first { it.name.equals(input, ignoreCase = true) }
            } catch (e: NoSuchElementException) {
                println("Wrong value! Try again!")
            }
        }
    }
}