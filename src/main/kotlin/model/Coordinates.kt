package org.example.model
import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val x: Int,
    val y: Float
) {
    init {
        require(x <= 806) { "X coordinate must be ≤ 806" }
        require(y <= 922) { "Y coordinate must be ≤ 922" }
    }
}