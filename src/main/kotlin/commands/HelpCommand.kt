package org.example.commands

import org.example.core.CollectionManager

class HelpCommand(private val commands: Map<String, CommandInterface>)  : Command (
    name = "help",
    description = "Display a list of all available commands."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        println("Available Commands:")
        commands.forEach{
            command -> println("${command.key} - ${command.value.getDescription()}")
        }
        println("execute_script <filename>: executes a script from a file.")
        println("exit : Exits the program without saving.")
    }


}