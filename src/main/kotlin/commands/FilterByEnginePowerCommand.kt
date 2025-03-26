package org.example.commands

import org.example.core.CollectionManager

class FilterByEnginePowerCommand :  FilterByCharacteristicCommand (
    name = "filter_by_engine_power",
    description = "Find all the elements with the specified engine power value.",
    size = 1
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        if(!checkSizeOfArgs(args.size)) {
            println("Error: Args can be size ${size}.")
            return
        }
        super.execute(listOf("enginePower", args[0]), collectionManager)
    }
}