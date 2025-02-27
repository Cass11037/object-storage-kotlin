package org.example

import org.example.commands.*
import org.example.core.CommandProcessor
import org.example.core.VehicleReader
import java.io.File
import java.util.*


fun main() {
    val scanner = Scanner(System.`in`)
    var fileName: String
    while (true) {
        println("Доступные файлы: data1.csv, data2.csv, data3.csv")
        println("Введите название файла, который хотите открыть: ")
        print("> ")
        fileName = scanner.nextLine().trim()
        val file = File(fileName)
        if (file.exists()) break
        else println("Файл не найден, попробуйте еще раз")
    }
    val vehicleReader = VehicleReader(scanner)
    val commands = listOf(
        HelpCommand(emptyMap()),
        AddCommand(vehicleReader),
        ClearCommand(),
        ShowCommand(),
        InfoCommand(),
    ).associateBy { it.getName() }
    CommandProcessor(commands, scanner, fileName).start()
}