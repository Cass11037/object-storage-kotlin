package org.example.commands

class HelpCommand(private val commands: List<Command>)  : BaseCommand (
    name = "help",
    info = "Вывести список всех доступных команд."
){
    fun execute () {
        println("Доступные команды:")
        commands.forEach{
            command -> println("${command.getName()} - ${command.getDescription()}")
        }
    }


}