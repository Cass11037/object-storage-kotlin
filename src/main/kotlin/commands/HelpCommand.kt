package org.example.commands

import IOManager
import org.example.core.CollectionManager

class HelpCommand(private val commands: Map<String, CommandInterface>)  : Command (
    name = "help",
    description = "Display a list of all available commands.",
    size = 0
){
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        ioManager.outputLine("Available Commands:")
        commands.forEach{
            command -> ioManager.outputLine("${command.key} - ${command.value.getDescription()}")
        }
        ioManager.outputLine("execute_script <filename>: executes a script from a file.")
        ioManager.outputLine("exit : Exits the program without saving.")
    }


}