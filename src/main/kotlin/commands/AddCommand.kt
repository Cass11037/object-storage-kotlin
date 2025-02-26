import BaseCommand
import Coordinates
import kotlinx.serialization.Contextual
import java.util.*

class AddCommand : BaseCommand (
    name = "add",
    descr = "Добавить новый элемент в коллекцию."
){
    override fun execute(args: List<String>) {
        try {
            if(args.size < 6) {
                println("Ошибка: Недостаточно элементов. Ожидается 6 эелемнетов.")
            } else {
                val name: String = args[0]
                val coordinates: Coordinates = Coordinates(args[1].toInt(), args[2].toFloat())
                val enginePower: Double = args[3].toDouble()
                val distanceTravelled: Double? = args[4].toDoubleOrNull()
                if (distanceTravelled == null) {
                    println("Предупреждение: Поле 'distanceTravelled' не задано или имеет некорректный формат.")
                }

                val type: VehicleType? = try {
                    VehicleType.valueOf(args[5])
                } catch (e: IllegalArgumentException) {
                    println("Ошибка: Некорректный тип транспортного средства.")
                    null
                }
                val fuelType: FuelType? = try {
                    FuelType.valueOf(args[6])
                } catch (e: IllegalArgumentException) {
                    println("Ошибка: Некорректный тип топлива.")
                    null
                }

            } catch (e: Exception) {
                println("Error loading data: ${e.message}")
            }
        }
    }
}