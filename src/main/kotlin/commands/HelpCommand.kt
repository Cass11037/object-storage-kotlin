package org.example.commands

import org.example.core.CollectionManager

class HelpCommand(private val commands: Map<String, CommandInterface>)  : Command (
    name = "help",
    description = "Display a list of all available commands."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        println("Доступные команды:")
        commands.forEach{
            command -> println("${command.key} - ${command.value.getDescription()}")
        }
    }


}