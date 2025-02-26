import java.util.LinkedList
class ShowCommand : BaseCommand(
    name = "show",
    descr = "Display all the items in the collection."
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