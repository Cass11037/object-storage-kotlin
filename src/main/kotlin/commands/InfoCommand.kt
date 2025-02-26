package org.example.commands

import BaseCommand

class InfoCommand : BaseCommand (
    name = "info",
    descr = "Выводит информацию о проекте."
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