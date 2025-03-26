package org.example.commands

import IOManager
import org.example.core.CollectionManager
import java.nio.file.Path

class SaveCommand : Command (
    name = "save",
    description = "Save the collection to a file.",
    size = 1
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size, size)) {
            ioManager.outputLine("Error: Args can be size ${args.size}.")
            return
        }
        if(args.isEmpty()) {
            collectionManager.saveToFile()
        } else {
            collectionManager.saveToFile(Path.of(args[0]))
        }

        ioManager.outputLine("Data saved to your file.")
    }
    private fun checkSizeOfArgs(f: Int, s: Int) : Boolean {
        return s>=f
    }
}