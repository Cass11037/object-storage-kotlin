package org.example.model

enum class FuelType (val type: String) {
    KEROSENE("kerosene"),
    DIESEL("diesel"),
    ALCOHOL("alcohol"),
    MANPOWER("manpower"),
    NUCLEAR("nuclear");
    companion object {
        fun fromString(type: String): FuelType? {
            return FuelType.entries.find { it.type == type }
        }
    }
}