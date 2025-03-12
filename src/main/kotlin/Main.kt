package org.example

import org.example.core.*
import org.example.commands.*
import java.io.File
import java.util.*

//теория
//комментарии
//отчет
//
//файл если ничего не введено то использовать перманентный
//проверять создание имени файла новго через регех
//Написать скрипт который будет создавать 10 объектов
//чето с сортировкой
//сейв в файл если без арг то в перма
//сейв уточнять в какой файл
//опять сделать loadfromfile
//и исправить id из-за этого
//
//сделать нормальный мейн



fun fileReader(scanner: Scanner): String {
    var fileName: String
    val regex = "^[\\w\\-]+\\.csv$".toRegex()
    while (true) {
        println("Enter file name: ")
        print("> ")
        fileName = scanner.nextLine().trim()
        val permaFile = File("Collection.csv")
        if(fileName==""){
            return "Collection.csv"
            else
        }

        if (!regex.matches(fileName)) {
            println("Invalid file name. The file name must contain only letters, digits, underscores, or hyphens and end with '.csv'.")
            continue
        }

        val file = File(fileName)
        val permaFile = File("Collection.csv")
        if (fileName.isEmpty() || fileName == ".csv") {
            if (!permaFile.exists()) {
                permaFile.createNewFile()
            }
            return "Collection.csv"
        } else if (!fileName.endsWith(".csv")) {
            println("File type must be csv and end in .csv")
            continue
        }
        if (file.exists()) {
            println("File '$fileName' already exists.")
        } else {
            if (file.createNewFile()) {
                println("File '$fileName' created successfully.")
                break
            } else {
                println("Unable to create file '$fileName'.")
            }
        }
    }
    return fileName
}

fun main() {
    val scanner = Scanner(System.`in`)
    val fileName = fileReader(scanner)
    val vehicleReader = VehicleReader(scanner)
    val commands = listOf(
        AddCommand(vehicleReader),
        AddIfMaxCommand(vehicleReader),
        AddIfMinCommand(vehicleReader),
        ClearCommand(),
        FilterByEnginePowerCommand(),
        HelpCommand(emptyMap()),
        InfoCommand(),
        MinByNameCommand(),
        RemoveAnyByEnginePowerCommand(),
        RemoveByIdCommand(),
        RemoveFirstCommand(),
        ShowCommand(),
        SaveCommand(),
        UpdateIdCommand(vehicleReader),

        ).associateBy { it.getName() }
    val help = HelpCommand(commands)
    val allCommands = listOf(
        help,
        AddCommand(vehicleReader),
        AddIfMaxCommand(vehicleReader),
        AddIfMinCommand(vehicleReader),
        ClearCommand(),
        FilterByEnginePowerCommand(),
        InfoCommand(),
        MinByNameCommand(),
        RemoveAnyByEnginePowerCommand(),
        RemoveByIdCommand(),
        RemoveFirstCommand(),
        ShowCommand(),
        SaveCommand(),
        UpdateIdCommand(vehicleReader),
    ).associateBy { it.getName() }
    CommandProcessor(allCommands, scanner, fileName).start()
}