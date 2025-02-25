interface Command {
    val name: String
    val description: String
    fun execute(args: List<String>): Boolean
}