interface Command {
    fun getName() : String
    fun getDescription(): String
    fun execute(args: List<String> = emptyList())
}