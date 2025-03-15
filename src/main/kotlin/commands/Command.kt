package org.example.commands

abstract class Command  (
    private val name: String,
    private val description: String,
    private val size: Int
) : CommandInterface {
    override fun getName(): String = name
    override fun getDescription(): String = description
    override fun checkSizeOfArgs(argsSize: Int) : Boolean {
        return argsSize == size

    }

    override fun checkSizeOfArgs(argsSize: Int, neededSize: Int): Boolean {
        return argsSize == neededSize
    }
}