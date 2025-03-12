package org.example.commands

abstract class Command  (
    private val name: String,
    private val description: String,
    private final val size: Int
) : CommandInterface {
    override fun getName(): String = name
    override fun getDescription(): String = description
    fun checkArgsSize(argsSize: Int) : Boolean {
        return size == argsSize
    }
}