package org.example.model
import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val x: Int,
    val y: Float
) {
    init {
        require(x <= 1000) { "X must be ≤1000" }
        require(y <= 1000) { "Y must be ≤1000" }
    }
}