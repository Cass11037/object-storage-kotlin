package org.example.model

enum class VehicleType (val type: String) {
    BOAT("boat"),
    BICYCLE("bicycle"),
    HOVERBOARD("hoverboard");
    companion object {
        // Функция для преобразования строки в VehicleType
        fun fromString(type: String): VehicleType? {
            return entries.find { it.type == type }
        }
    }
}