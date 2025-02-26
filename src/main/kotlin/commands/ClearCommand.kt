package org.example.commands

import BaseCommand

class ClearCommand : BaseCommand (
    name = "clear",
    descr = "Clear collection."
) {
    override fun execute(args: List<String>) {
        collection // очистить
    }
}