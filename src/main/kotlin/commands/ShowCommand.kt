class ShowCommand : BaseCommand(
    name = "show",
    description = "Display all the items in the collection."
) {
    override fun execute(args: List<String>) {
        if(collection.getAll().isEmpty()) {
            println("Collection is empty.")
        } else {
            collection.getAll().forEach {
                vehicle -> println(vehicle)
            }
        }
    }
}