import io.mockk.*
import org.example.commands.*
import org.example.core.*
import org.example.model.*
import org.junit.jupiter.api.Test
import kotlin.test.*

class AppTest {

    // Тест для проверки сравнения Vehicle
    @Test
    fun testVehicleComparison() {
        val vehicle1 = Vehicle(
            id = 1,
            name = "A",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 100.0,
            distanceTravelled = 1000.0,
            type = VehicleType.HOVERBOARD,
            fuelType = FuelType.DIESEL
        )
        val vehicle2 = Vehicle(
            id = 2,
            name = "B",
            coordinates = Coordinates(20, 30f),
            creationDate = System.currentTimeMillis(),
            enginePower = 200.0,
            distanceTravelled = 2000.0,
            type = VehicleType.HOVERBOARD,
            fuelType = FuelType.NUCLEAR
        )

        assertTrue(vehicle1 < vehicle2)
        assertTrue(vehicle2 > vehicle1)
        assertEquals(0, vehicle1.compareTo(vehicle1))
    }

    // Тест для проверки работы с enum
    @Test
    fun testVehicleEnums() {
        val vehicle = Vehicle(
            id = 1,
            name = "EnumTest",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 100.0,
            distanceTravelled = 1000.0,
            type = VehicleType.BOAT,
            fuelType = FuelType.NUCLEAR
        )

        assertEquals(VehicleType.BOAT, vehicle.type)
        assertEquals(FuelType.NUCLEAR, vehicle.fuelType)
        assertEquals("BOAT", vehicle.type?.name)
        assertEquals("NUCLEAR", vehicle.fuelType?.name)
    }

    @Test
    fun testUpdateIdCommand() {
        val mockIO = mockk<IOManager>()
        val mockCollection = mockk<CollectionManager>()
        val mockReader = mockk<VehicleReader>()

        val command = UpdateIdCommand(mockReader)
        val vehicle = Vehicle(
            id = 1,
            name = "Car",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 150.0,
            distanceTravelled = 1000.0,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.DIESEL
        )

        every { mockCollection.getById(1) } returns vehicle
        every { mockReader.readUpdatesForVehicle(vehicle) } just Runs
        every { mockIO.outputLine(any<String>()) } just Runs

        command.execute(listOf("1"), mockCollection, mockIO)

        verify(exactly = 1) { mockReader.readUpdatesForVehicle(vehicle) }
        verify(exactly = 1) { mockIO.outputLine("Vehicle 1 was updated.") }
    }

    //Команда HelpCommand выводит список доступных команд. Мы будем мокировать IOManager для проверки корректности вывода
    @Test
    fun testHelpCommand() {
        val mockIO = mockk<IOManager>()
        val commands = mapOf(
            "add" to AddCommand(mockk()),
            "info" to InfoCommand(),
            "help" to HelpCommand(emptyMap())
        )

        val command = HelpCommand(commands)
        every { mockIO.outputLine(any<String>()) } just Runs

        command.execute(emptyList(), mockk(), mockIO)

        verify(exactly = 1) { mockIO.outputLine("Available Commands:") }
        verify(exactly = 1) { mockIO.outputLine("add - Add new vehicle to collection") }
        verify(exactly = 1) { mockIO.outputLine("info - Displays project information.") }
        verify(exactly = 1) { mockIO.outputLine("help - Display a list of all available commands.") }
        verify(exactly = 1) { mockIO.outputLine("execute_script <filename>: executes a script from a file.") }
        verify(exactly = 1) { mockIO.outputLine("exit : Exits the program without saving.") }
    }
    // Тест для удаления транспортного средства по ID
    @Test
    fun testRemoveById() {
        val manager = CollectionManager("test.csv")
        val vehicle = Vehicle(
            id = 1,
            name = "BOAT",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 150.0,
            distanceTravelled = 3000.0,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.DIESEL
        )

        manager.addVehicle(vehicle)
        assertEquals(1, manager.size())

        manager.deleteElement(1)
        assertEquals(0, manager.size())
        assertNull(manager.getById(1))
    }

    // Тест для команды SaveCommand
    @Test
    fun testSaveCommand() {
        val mockIO = mockk<IOManager>()
        val mockCollection = mockk<CollectionManager>()

        val command = SaveCommand()
        every { mockCollection.saveToFile() } returns emptyList()
        every { mockIO.outputLine(any<String>()) } just Runs

        command.execute(emptyList(), mockCollection, mockIO)

        verify { mockCollection.saveToFile() }
        verify { mockIO.outputLine("Data saved to your file.") }
    }


    // Тест для фильтрации по мощности двигателя
    @Test
    fun testFilterByEnginePower() {
        val manager = CollectionManager("test.csv")
        val vehicle1 = Vehicle(
            id = 1,
            name = "Car1",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 100.0,
            distanceTravelled = 1000.0,
            type = VehicleType.HOVERBOARD,
            fuelType = FuelType.DIESEL
        )
        val vehicle2 = Vehicle(
            id = 2,
            name = "Car2",
            coordinates = Coordinates(20, 30f),
            creationDate = System.currentTimeMillis(),
            enginePower = 200.0,
            distanceTravelled = 2000.0,
            type = VehicleType.BOAT,
            fuelType = FuelType.DIESEL
        )

        manager.addVehicle(vehicle1)
        manager.addVehicle(vehicle2)

        val filtered = manager.filterByCharacteristic("enginePower", "200.0")
        assertEquals(1, filtered.size)
        assertEquals("Car2", filtered[0].name)
    }


    // Тест для валидации Vehicle
    @Test
    fun testVehicleValidation() {
        assertFailsWith<IllegalArgumentException> {
            Vehicle(
                id = -1, // Невалидный ID
                name = "Test",
                coordinates = Coordinates(10, 20f),
                creationDate = System.currentTimeMillis(),
                enginePower = 100.0,
                distanceTravelled = 1000.0,
                type = VehicleType.BOAT,
                fuelType = FuelType.DIESEL
            )
        }

        assertFailsWith<IllegalArgumentException> {
            Vehicle(
                id = 1,
                name = "", // Пустое имя
                coordinates = Coordinates(10, 20f),
                creationDate = System.currentTimeMillis(),
                enginePower = 100.0,
                distanceTravelled = 1000.0,
                type = VehicleType.BOAT,
                fuelType = FuelType.NUCLEAR
            )
        }
    }


    // Тест для CollectionManager(добавление и получение транспортного средства)
    @Test
    fun testAddAndGetVehicle() {
        val manager = CollectionManager("test.csv")
        val vehicle = Vehicle(
            id = 1,
            name = "Truck",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 200.0,
            distanceTravelled = 5000.0,
            type = null,
            fuelType = null
        )

        manager.addVehicle(vehicle)

        assertEquals(1, manager.size())
        assertNotNull(manager.getById(1))
        assertEquals("Truck", manager.getById(1)?.name)
    }

    // Тест для AddIfMaxCommand - добавление только если транспортное средство максимальное
    @Test
    fun testAddIfMaxCommand() {
        val mockIO = mockk<IOManager>()
        val mockCollection = mockk<CollectionManager>()
        val mockReader = mockk<VehicleReader>()

        val command = AddIfMaxCommand(mockReader)
        val newVehicle = Vehicle(
            id = 2,
            name = "MaxCar",
            coordinates = Coordinates(500, 600f),
            creationDate = System.currentTimeMillis(),
            enginePower = 300.0,
            distanceTravelled = 10000.0,
            type = VehicleType.BICYCLE,
            fuelType = FuelType.DIESEL
        )

        val maxVehicle = Vehicle(
            id = 1,
            name = "OldCar",
            coordinates = Coordinates(10, 20f),
            creationDate = System.currentTimeMillis(),
            enginePower = 200.0,
            distanceTravelled = 5000.0,
            type = VehicleType.HOVERBOARD,
            fuelType = FuelType.DIESEL
        )

        every { mockReader.readVehicle() } returns newVehicle
        every { mockCollection.getMax() } returns maxVehicle
        every { mockCollection.addVehicle(newVehicle) } returns newVehicle
        every { mockIO.outputLine(any<String>()) } returns Unit

        command.execute(emptyList(), mockCollection, mockIO)

        verify { mockCollection.addVehicle(newVehicle) }
        verify { mockIO.outputLine("Vehicle added with ID: ${newVehicle.id}") }
    }
}