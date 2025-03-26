package org.example

import org.example.core.*
import java.io.File
import java.io.IOException
import java.util.*

//теория
//комментарии
//отчет
//
//сейв в файл если без арг то в перма
//сейв уточнять в какой файл
//опять сделать loadfromfile
//


fun fileReader(scanner: Scanner): String {
    val permanentFileName = "Collection.csv"
    val maxAttempts = 3 //макс попытки на создание файла
    var attempts = 0
    val csvRegex = "^[a-zA-Z0-9_\\-() ]+\\.csv$".toRegex()
    //функция для создания файла как обычного так и перманентного
    fun createFile(fileName: String): String? {
        return try {
            val file = File(fileName)
            when {
                file.exists() -> {
                    println("File '$fileName' already exists.")
                    fileName
                }
                file.createNewFile() -> {
                    println("File '$fileName' created successfully.")
                    fileName
                }
                else -> {
                    println("Failed to create '$fileName'.")
                    null
                }
            }
        } catch (e: SecurityException) {
            println("Security error: ${e.message}")
            null
        } catch (e: IOException) {
            println("IO error: ${e.message}")
            null
        }
    }
    while (attempts < maxAttempts) {
        println("Would you like to:")
        println("1. Create new or load existing CSV file")
        println("2. Use permanent file ($permanentFileName)")
        print("> ")
        when (scanner.nextLine().trim().lowercase()) {
            "1", "y", "yes", "да" -> {
                while (attempts < maxAttempts) {
                    println("Enter new file name (or 'exit' to use permanent file):")
                    print("> ")
                    val input = scanner.nextLine().trim()
                    if (input.equals("exit", ignoreCase = true)) return permanentFileName
                    when {
                        !csvRegex.matches(input) -> {
                            println("Invalid name. Allowed: letters, numbers, _-() and .csv extension")
                        }
                        input.equals(permanentFileName, ignoreCase = true) -> {
                            println("Cannot use permanent file name for new file")
                        }

                        else -> {
                            createFile(input)?.let { return it }
                        }
                    }
                    attempts++
                }
            }
            "2", "n", "no", "нет" -> {
                val file = File(permanentFileName)
                return when {
                    file.exists() -> permanentFileName
                    else -> {
                        createFile(permanentFileName) ?: run {
                            println("Fatal error: Cannot create permanent file")
                            throw IllegalStateException("Permanent file creation fail")
                        }
                    }
                }
            }
            else -> {
                println("Invalid choice. Please enter 'yes' or 'no'.")
                attempts++
            }
        }
    }
    throw IllegalStateException("Too many invalid attempts, BYE!")
}

fun main() {
    val scanner = Scanner(System.`in`)
    val fileName = fileReader(scanner)
    CommandProcessor(scanner, fileName).start()
}