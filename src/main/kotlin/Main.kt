package org.example

import org.example.core.*
import org.example.commands.*
import java.io.File
import java.util.*

fun fileReader(scanner: Scanner) : String {
    var fileName: String
    while (true) {
        println("Введите название файла, в котором будете работать: ")
        print("> ")
        fileName = scanner.nextLine().trim()
        val file = File(fileName)
        if (!fileName.endsWith(".csv")) {
            println("Файл должен иметь расширение .csv")
            continue;
        }
        if(file.exists()) {
            println("Файл '$fileName' уже существует.")
        } else {
            if(file.createNewFile()) {
                println("Файл '$fileName' успешно создан.")
                break;
            }else {
                println("Не удалось создать файл '$fileName'.")
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