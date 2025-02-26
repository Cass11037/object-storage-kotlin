package org.example

import org.example.commands.AddCommand
import org.example.commands.ClearCommand
import org.example.commands.ShowCommand
import org.example.core.CollectionManager
import org.example.core.CommandProcessor
import org.example.core.VehicleReader
import java.io.File
import java.util.*


fun main() {
    val scanner = Scanner(System.`in`)
    val vehicleReader = VehicleReader(scanner)
    var fileName=""
    while (true) {
        println("Доступные файлы: data1.json, data2.json, data3.json")
        println("Введите название файла, который хотите открыть: ")
        fileName = scanner.nextLine().trim()
        val file = File(fileName)
        if (file.exists()) break
        else println("Файл не найден, попробуйте еще раз")
    }
    val commands = listOf(
        AddCommand(vehicleReader),
        ClearCommand(),
        ShowCommand()
    ).associateBy { it.getName() }

    val collectionManager = CollectionManager(fileName)
    CommandProcessor(commands, scanner, collectionManager).start()
    collectionManager.saveToFile()
    println("Data saved to $fileName")
}