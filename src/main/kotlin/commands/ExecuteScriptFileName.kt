package org.example.commands

import org.example.core.CollectionManager

class ExecuteScriptFileName :Command (
    name = "execute_script",
    description = ""
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
//        if (args.isEmpty()) {
//            println("Error: File name is missing. Usage: execute_script <file_name>")
//            return
//        }
//
//        val fileName = args[0]
//        val file = File(fileName)
//
//        // Проверка существования файла
//        if (!file.exists()) {
//            println("Error: File '$fileName' not found.")
//            return
//        }
//
//        // Чтение файла построчно
//        val lines = file.readLines()
//
//        // Создаем CommandProcessor для выполнения команд
//        val commandProcessor = CommandProcessor(collectionManager)
//
//        // Выполняем каждую команду из файла
//        for (line in lines) {
//            if (line.isBlank()) continue // Пропускаем пустые строки
//
//            println("Executing: $line")
//            commandProcessor.processCommand(line.trim())
        //}
    }
}