package org.example.commands

import org.example.core.CollectionManager
import java.nio.file.Path

class SaveCommand : Command (
    name = "save",
    description = "Save the collection to a file.",
    size = 1
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size, size)) {
            println("Error: Args can be size ${args.size}.")
            return
        }
        if(args.isEmpty()) {
            collectionManager.saveToFile()
        } else {
            collectionManager.saveToFile(Path.of(args[0]))
        }

        println("Data saved to your file.")
    }
    private fun checkSizeOfArgs(f: Int, s: Int) : Boolean {
        return s>=f
    }
}