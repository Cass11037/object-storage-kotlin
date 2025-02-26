import java.util.LinkedList
class ShowCommand : BaseCommand(
    name = "show",
    info = "Вывести все элементы коллекции."
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