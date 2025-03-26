//package org.example.tests
//
//import io.mockk.*
//import org.example.commands.*
//import org.example.core.CollectionManager
//import org.example.core.CommandProcessor
//import org.example.core.VehicleReader
//import org.example.model.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.PrintStream
//import java.util.Scanner
//import kotlin.test.assertEquals
//import kotlin.test.assertNotNull
//import kotlin.test.assertTrue
//
//class AppTest {
//    private val testFilename = "test_data.csv"
//
//    @BeforeEach
//    fun setup() {
//        File(testFilename).delete()
//    }
//
//    // 1. При старте CommandProcessor должна быть выполнена команда help
//    @Test
//    fun testHelpCommandExecutedOnStart() {
//        val ioManager = Scanner("name\nexit\n")
//        val helpCommand = spyk(object : Command("name", "description", 0) {
//            override fun getName(): String = "help"
//            override fun getDescription(): String = "Help command"
//            override fun execute(args: List<String>, collectionManager: CollectionManager) {
//                println("Help executed")
//            }
//        })
//        val commands = mapOf("help" to helpCommand)
//        val processor = CommandProcessor(commands, ioManager, testFilename)
//        processor.start()
//        verify(exactly = 1) { helpCommand.execute(emptyList(), any()) }
//    }
//
//    // 2. Если введена корректная команда, то она должна выполняться с переданными аргументами
//    @Test
//    fun processCommandValidCommandTest() {
//        val mockCommand = spyk(object : Command("test", "desc", 2) {
//            override fun execute(args: List<String>, collectionManager: CollectionManager) {
//                println("Executed with args: $args")
//            }
//        })
//        val commands = mapOf("test" to mockCommand)
//        val ioManager = Scanner("test arg1 arg2\nexit\n")
//        val processor = CommandProcessor(commands, ioManager, "dummy.csv")
//        every { mockCommand.execute(any(), any()) } just Runs
//        processor.start()
//        verify { mockCommand.execute(listOf("arg1", "arg2"), processor.collectionManager) }
//    }
//
//    // 3. Проверка добавления транспортного средства и поиска по id
//    @Test
//    fun testAddVehicleAndGetById() {
//        val collectionManager = CollectionManager(testFilename)
//        val vehicle = Vehicle(
//            id = 0,
//            name = "Test Vehicle",
//            coordinates = Coordinates(10, 10f),
//            creationDate = System.currentTimeMillis(),
//            enginePower = 100.0,
//            distanceTravelled = null,
//            type = VehicleType.BOAT,
//            fuelType = FuelType.DIESEL
//        )
//        collectionManager.addVehicle(vehicle)
//        val added = collectionManager.getById(1)
//        assertNotNull(added)
//        assertEquals("Test Vehicle", added?.name)
//    }
//
//    // 4. Проверка вызова метода saveToFile у CollectionManager
//    @Test
//    fun testSaveCommand() {
//        val collectionManager = spyk(CollectionManager(testFilename))
//        val saveCommand = SaveCommand()
//        saveCommand.execute(emptyList(), collectionManager)
//        verify { collectionManager.saveToFile() }
//    }
//
//    // 5. При пустой коллекции должен выводиться соответствующий текст
//    @Test
//    fun testShowCommandEmptyCollection() {
//        val collectionManager = CollectionManager(testFilename) // коллекция пустая
//        //System.out в ByteArrayOutputStream
//        val originalOut = System.out
//        val baos = ByteArrayOutputStream()
//        val ps = PrintStream(baos)
//        System.setOut(ps)
//        val showCommand = ShowCommand()
//        showCommand.execute(emptyList(), collectionManager)
//        // Восстанавливаем оригинальный System.out
//        System.out.flush()
//        System.setOut(originalOut)
//        val output = baos.toString()
//        assertTrue(output.contains("Collection is empty."))
//    }
//
//    //6. Проверка работы processCommand
//    @Test
//    fun processCommandCorrectnessTest () {
//        val mockCommand = mockk<Command> {
//            every { getName() } returns "test"
//        }
//        val commands = mapOf("test" to mockCommand)
//        val ioManager = Scanner("test arg1 arg2\nexit")
//        val commandProcessor = CommandProcessor(commands, ioManager, "test.csv")
//        every { mockCommand.execute(any(), any()) } just Runs
//        commandProcessor.start()
//        verify { mockCommand.execute(listOf("arg1", "arg2"), commandProcessor.collectionManager) }
//    }
//
//    // 7. При пустой коллекции выводится сообщение "Коллекция пуста"
//    @Test
//    fun testInfoCommandEmptyCollection() {
//        val collectionManager = CollectionManager(testFilename)
//        val originalOut = System.out
//        val baos = ByteArrayOutputStream()
//        System.setOut(PrintStream(baos))
//        val infoCommand = InfoCommand()
//        infoCommand.execute(emptyList(), collectionManager)
//        System.out.flush()
//        System.setOut(originalOut)
//        val output = baos.toString()
//        assertTrue(output.contains("Collection is empty"))
//    }
//
//    //8. Тест фильтрации по мощности двигателя
//    @Test
//    fun testFilterByEnginePowerCommand() {
//        val collectionManager = CollectionManager(testFilename)
//        val vehicle1 = Vehicle(
//            id = 0,
//            name = "Car A",
//            coordinates = Coordinates(1, 1f),
//            creationDate = System.currentTimeMillis(),
//            enginePower = 150.0,
//            distanceTravelled = null,
//            type = VehicleType.BICYCLE,
//            fuelType = FuelType.ALCOHOL
//        )
//        val vehicle2 = Vehicle(
//            id = 0,
//            name = "Car B",
//            coordinates = Coordinates(2, 2f),
//            creationDate = System.currentTimeMillis(),
//            enginePower = 200.0,
//            distanceTravelled = null,
//            type = VehicleType.HOVERBOARD,
//            fuelType = FuelType.NUCLEAR
//        )
//        collectionManager.addVehicle(vehicle1)
//        collectionManager.addVehicle(vehicle2)
//
//        val originalOut = System.out
//        val baos = ByteArrayOutputStream()
//        System.setOut(PrintStream(baos))
//
//        val filterCommand = FilterByEnginePowerCommand()
//        filterCommand.execute(listOf("200.0"), collectionManager)
//
//        System.out.flush()
//        System.setOut(originalOut)
//        val output = baos.toString()
//
//        assertTrue(output.contains("Car B"))
//    }
//
//
//    // 9. Тест ClearCommand, после вызова команда коллекция должна быть пустой
//    @Test
//    fun testClearCommand() {
//        val collectionManager = CollectionManager(testFilename)
//        val vehicle = Vehicle(
//            id = 0,
//            name = "ToClear",
//            coordinates = Coordinates(5, 5f),
//            creationDate = System.currentTimeMillis(),
//            enginePower = 100.0,
//            distanceTravelled = null,
//            type = VehicleType.BOAT,
//            fuelType = FuelType.DIESEL
//        )
//        collectionManager.addVehicle(vehicle)
//        assertTrue(collectionManager.size() > 0)
//        val clearCommand = ClearCommand()
//        clearCommand.execute(emptyList(), collectionManager)
//        assertTrue(collectionManager.isEmpty())
//    }
//
//    // 10. Проверка AddIfMax
//    @Test
//    fun testAddIfMaxCommandAddsNewVehicleWhenItIsMax() {
//        val collectionManager = CollectionManager(testFilename)
//        val existingVehicle = Vehicle(
//            id = 0,
//            name = "Existing",
//            coordinates = Coordinates(10, 10f),
//            creationDate = System.currentTimeMillis(),
//            enginePower = 150.0,
//            distanceTravelled = null,
//            type = VehicleType.BOAT,
//            fuelType = FuelType.DIESEL
//        )
//        collectionManager.addVehicle(existingVehicle)
//
//        // Мокаем VehicleReader, чтобы вернуть новый транспорт с большим enginePower (200)
//        val vehicleReader = mockk<VehicleReader>()
//        val newVehicle = Vehicle(
//            id = 0,
//            name = "NewMax",
//            coordinates = Coordinates(20, 20f),
//            creationDate = System.currentTimeMillis(),
//            enginePower = 200.0,
//            distanceTravelled = null,
//            type = VehicleType.HOVERBOARD,
//            fuelType = FuelType.NUCLEAR
//        )
//        every { vehicleReader.readVehicle() } returns newVehicle
//
//        // Создаем команду AddIfMaxCommand с замоканным VehicleReader
//        val addIfMaxCommand = AddIfMaxCommand(vehicleReader)
//
//        // Выполняем команду
//        addIfMaxCommand.execute(emptyList(), collectionManager)
//
//        // Проверяем, что в коллекции теперь 2 элемента
//        assertEquals(2, collectionManager.size())
//        val addedVehicle = collectionManager.getAll().find { it.name == "NewMax" }
//        assertNotNull(addedVehicle)
//    }
//}