package org.example.commands
import org.example.core.CollectionManager

abstract class Command  (
    private val name: String,
    private val description: String
) : CommandInterface {
    override fun getName(): String = name
    override fun getDescription(): String = description
}