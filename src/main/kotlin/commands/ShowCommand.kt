package org.example.commands
import java.util.LinkedList
import org.example.model.*
class ShowCommand : BaseCommand(
    name = "show",
    info = "Вывести все элементы коллекции."
) {
    fun execute () {
        if(collection.isEmpty()) {
            println("Collection is empty.")
        } else {

        }
    }
}