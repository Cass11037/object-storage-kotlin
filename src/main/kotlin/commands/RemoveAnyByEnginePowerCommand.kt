package org.example.commands

import org.example.core.CollectionManager

class RemoveAnyByEnginePowerCommand : RemoveAnyByCharacteristicCommand(
    name = "remove_any_by_engine_power",
    description = "Remove one element whose enginePower matches the specified value."
) {
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if (args.isEmpty()) {
            println("Error: Engine power value not specified.")
            return
        }
        super.execute(listOf("enginePower", args[0]), collectionManager)
    }
}