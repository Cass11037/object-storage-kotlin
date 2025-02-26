package org.example.commands

abstract class BaseCommand  (
    private val name: String,
    private val info: String
) : Command {
    override fun getName(): String = name
    override fun getDescription(): String = info
}