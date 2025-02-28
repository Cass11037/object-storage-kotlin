package org.example.core

import org.example.model.*
import java.util.Scanner

class VehicleReader(private val scanner: Scanner) {
    companion object {
        private var nextId = 1 // Общая переменная для всех экземпляров
    }

    fun readVehicle(): Vehicle {
        return Vehicle(
            id = nextId++,  // Временное значение
            name = readNonEmptyString("Название транспортного средства"),
            coordinates = readCoordinates(),
            creationDate = System.currentTimeMillis(),
            enginePower = readPositiveDouble("Мощность двигателя"),
            distanceTravelled = readOptionalDouble("Пройденная дистанция"),
            type = readEnum("Тип транспорта", VehicleType::class.java),
            fuelType = readEnum("Тип топлива", FuelType::class.java)
        )
    }

    private fun readCoordinates(): Coordinates {
        return Coordinates(
            x = readBoundedInt("Координата X", max = 806),
            y = readBoundedFloat("Координата Y", max = 922f)
        )
    }
    private fun readNonEmptyString(prompt: String): String {
        while (true) {
            print("$prompt: ")
            val input = scanner.nextLine().trim()
            if (input.isNotEmpty()) return input
            println("Поле не может быть пустым!")
        }
    }

    private fun readBoundedInt(prompt: String, min: Int = Int.MIN_VALUE, max: Int): Int {
        while (true) {
            print("$prompt (<$max): ")
            val input = scanner.nextLine()
            try {
                val value = input.toInt()
                if (value in min..max) return value
                println("Значение должно быть между $min и $max")
            } catch (e: NumberFormatException) {
                println("Некорректное целое число!")
            }
        }
    }
    private fun readBoundedFloat(prompt: String, max: Float): Float {
        while (true) {
            print("$prompt (макс. $max): ")
            val input = scanner.nextLine()
            try {
                val value = input.toFloat()
                if (value <= max) return value
                println("Значение не должно превышать $max")
            } catch (e: NumberFormatException) {
                println("Некорректное число!")
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
                println("Значение должно быть больше 0")
            } catch (e: NumberFormatException) {
                println("Некорректное число!")
            }
        }
    }
    private fun readOptionalDouble(prompt: String): Double? {
        print("$prompt (оставьте пустым если нет значения): ")
        val input = scanner.nextLine().trim()
        return if (input.isEmpty()) {
            null
        } else {
            try {
                input.toDouble().takeIf { it > 0 }
                    ?: throw IllegalArgumentException("Значение должно быть положительным")
            } catch (e: NumberFormatException) {
                println("Некорректное число! Поле будет null")
                null
            }
        }
    }
    //TODO simplify
    private inline fun <reified T : Enum<T>> readEnum(prompt: String, enumClass: Class<T>): T? {
        val values = enumClass.enumConstants.joinToString { it.name }
        println("$prompt (доступные значения: $values)")
        while (true) {
            print("Введите значение (оставьте пустым для отмены): ")
            val input = scanner.nextLine().trim()
            if (input.isEmpty()) return null
            try {
                return enumClass.enumConstants.first { it.name.equals(input, ignoreCase = true) }
            } catch (e: NoSuchElementException) {
                println("Некорректное значение! Попробуйте снова.")
            }
        }
    }
}