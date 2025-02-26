class HelpCommand(private val commands: List<Command>)  : BaseCommand (
    name = "help",
    descr = "Вывести список всех доступных команд."
){
    override fun execute(args: List<String>) {
        println("Доступные команды:")
        commands.forEach{
            command -> println("${command.getName()} - ${command.getDescription()}")
        }
    }


}