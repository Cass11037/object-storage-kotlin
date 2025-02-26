package org.example.commands


import org.example.core.CollectionManager

abstract class BaseCommand(
    private val name: String,
    private val description: String,
    protected val collection: CollectionManager
) : Command {
    override fun getName(): String = name
    override fun getDescription(): String = description
}