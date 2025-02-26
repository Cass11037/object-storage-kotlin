
import CollectionManager
import VehicleReader
import commands.BaseCommand

class InfoCommand(
    collection: CollectionManager,
    private val reader: VehicleReader
) :BaseCommand(
    name = "info",
    description = "Выводит информацию о проекте.", collection
){
    override fun execute(args: List<String>) {
        println("Информация:")
        println("Тип коллекции: ${collection.getAll()::class.simpleName}")
        println("Количество элементов: ${collection.getAll().size}")
        if (collection.getAll().isNotEmpty()) {
            println("Дата инициализации: ${collection.getAll().first().creationDate}")
        } else {
            println("Коллекция пуста.")
        }
    }
}