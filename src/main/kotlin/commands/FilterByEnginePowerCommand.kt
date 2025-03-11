package org.example.commands

import org.example.core.CollectionManager

class FilterByEnginePowerCommand :  FilterByCharacteristicCommand (
    name = "filter_by_engine_power",
    description = "Find all the elements with the specified engine power value."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if (args.isEmpty()) {
            println("Error: Engine power value not specified.")
            return
        }
        super.execute(listOf("enginePower", args[0]), collectionManager)
    }
}