package org.example.commands


import org.example.core.CollectionManager
import org.example.core.VehicleReader

class InfoCommand(
    private val reader: VehicleReader
) :Command(
    name = "info",
    description = "Выводит информацию о проекте."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        println("Информация:")
        println("Тип коллекции: ${collectionManager.getAll()::class.simpleName}")
        println("Количество элементов: ${collectionManager.getAll().size}")
        if (collectionManager.getAll().isNotEmpty()) {
            println("Дата инициализации: ${collectionManager.getAll().first().creationDate}")
        } else {
            println("Коллекция пуста.")
        }
    }
}