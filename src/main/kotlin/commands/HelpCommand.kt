class HelpCommand(private val commands: List<Command>)  : BaseCommand (
    name = "help",
    descr = "Display a list of all available commands."
){
    override fun execute(args: List<String>) {
        println("Доступные команды:")
        commands.forEach{
            command -> println("${command.getName()} - ${command.getDescription()}")
        }
    }


}