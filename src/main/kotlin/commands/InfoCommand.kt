package org.example.commands


import org.example.core.CollectionManager
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InfoCommand :Command(
    name = "info",
    description = "Выводит информацию о проекте."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        println("Информация:")
        println("Тип коллекции: ${collectionManager.getAll()::class.simpleName}")
        println("Количество элементов: ${collectionManager.size()}")
        if (collectionManager.getAll().isNotEmpty()) {
            val readableDate = Instant.ofEpochMilli(collectionManager.getAll().first().creationDate)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            println("Дата инициализации: $readableDate")
        } else {
            println("Коллекция пуста.")
        }
    }
}