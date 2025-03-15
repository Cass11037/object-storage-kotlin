package org.example

import org.example.core.*
import org.example.commands.*
import java.io.File
import java.util.*

fun fileReader(scanner: Scanner) : String {
    var fileName: String
    while (true) {
        println("Enter file name: ")
        print("> ")
        fileName = scanner.nextLine().trim()
        val file = File(fileName)
        if (!fileName.endsWith(".csv")) {
            println("File type must be csv and end in .csv")
            continue;
        }
        if(file.exists()) {
            println("File '$fileName' already exists.")
        } else {
            if(file.createNewFile()) {
                println("File '$fileName' created successfully.")
                break;
            }else {
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