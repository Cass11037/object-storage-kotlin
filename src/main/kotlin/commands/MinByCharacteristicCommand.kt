package org.example.commands

import org.example.core.CollectionManager

abstract class MinByCharacteristicCommand (
    name: String,
    description: String,
    size: Int
) : Command(
    name = name,
    description = description,
    size = size
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if (args.isEmpty() || args.size != 1) {
            println("Error: Args can not be empty.")
            return
        }
        val vehicle = collectionManager.getMin(args[0])
        if(vehicle == null)  {
            println("No vehicle found.")
            return
        }
        println("Element found with minimal $args[0]: $vehicle")
    }
}