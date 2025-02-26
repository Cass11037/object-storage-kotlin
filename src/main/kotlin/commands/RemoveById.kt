package org.example.commands

import BaseCommand

class RemoveById : BaseCommand (
    name = "remove_by_id",
    descr = "Delete an item from the collection by its id."
) {
    override fun execute(args: List<String>) {

    }
}