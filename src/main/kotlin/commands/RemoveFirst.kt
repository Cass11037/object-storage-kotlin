package org.example.commands

import org.example.core.CollectionManager

class RemoveFirst : Command (
    name = "remove_first",
    description = "Delete a first item in collection."
){
    override fun execute(args: List<String>, collectionManager: CollectionManager) {
        collectionManager.deleteByNumber(0)
    }

}