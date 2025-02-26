package org.example.commands

import org.example.core.CollectionManager

interface CommandInterface {
    fun getName() : String
    fun getDescription(): String
    fun execute(args: List<String> = emptyList(),collectionManager: CollectionManager)
}