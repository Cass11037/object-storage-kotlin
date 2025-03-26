package org.example.commands

import IOManager
import org.example.core.CollectionManager

class RemoveAnyByEnginePowerCommand : RemoveAnyByCharacteristicCommand(
    name = "remove_any_by_engine_power",
    description = "Remove one element whose enginePower matches the specified value.",
    size = 1
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager, ioManager: IOManager) {
        if(!checkSizeOfArgs(args.size)) {
            ioManager.outputLine("Error: Args can be size ${size}.")
            return
        }
        /*if (args.isEmpty()) {
            ioManager.outputLine("Error: Engine power value not specified.")
            return
        }*/
        super.execute(listOf("enginePower", args[0]), collectionManager, ioManager)
    }
}