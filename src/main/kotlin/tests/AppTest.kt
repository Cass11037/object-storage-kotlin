//import io.mockk.*
//import org.example.core.*
//import org.example.model.*
//import org.example.commands.*
//import org.junit.jupiter.api.Test
//
//class AppTest {
//
//    @Test
//    fun `test add command execution`() {
//        // Arrange
//        val mockCollection = mockk<CollectionManager>()
//        val mockIO = mockk<IOManager>()
//        val mockReader = mockk<VehicleReader>()
//
//        every { mockReader.readVehicle() } returns Vehicle(
//            1, "Test", Coordinates(100, 200f),
//            System.currentTimeMillis(), 150.0, 5000.0,
//            VehicleType.BOAT, FuelType.DIESEL
//        )
//        every { mockCollection.addVehicle(any<Vehicle>()) } returns mockk()
//        every { mockIO.outputLine(any<String>()) } just Runs
//
//        val command = AddCommand(mockReader)
//
//        // Act
//        command.execute(emptyList(), mockCollection, mockIO)
//
//        // Assert
//        verify { mockCollection.addVehicle(any<Vehicle>()) }
//        verify { mockIO.outputLine("Vehicle added with ID: 1") }
//    }
//
//    @Test
//    fun `test remove_by_id command`() {
//        // Arrange
//        val mockCollection = mockk<CollectionManager>()
//        val mockIO = mockk<IOManager>()
//
//        every { mockCollection.getById(1) } returns mockk()
//        every { mockCollection.deleteElement(any<Int>()) } just Runs
//        every { mockIO.outputLine(any<String>()) } just Runs
//
//        val command = RemoveByIdCommand()
//
//        // Act
//        command.execute(listOf("1"), mockCollection, mockIO)
//
//        // Assert
//        verify { mockCollection.deleteElement(1) }
//        verify { mockIO.outputLine("Element removed: ...") }
//    }
//
//
//    @Test
//    fun `test remove_first command`() {
//        // Arrange
//        val mockCollection = mockk<CollectionManager>()
//        val mockIO = mockk<IOManager>()
//
//        every { mockCollection.isEmpty() } returns false
//        every { mockCollection.deleteByNumber(any<Int>()) } just Runs
//        every { mockIO.outputLine("First element removed.") } just Runs
//
//        val command = RemoveFirstCommand()
//
//        // Act
//        command.execute(emptyList(), mockCollection, mockIO)
//
//        // Assert
//        verify { mockCollection.deleteByNumber(0) }
//        verify { mockIO.outputLine("First element removed.") }
//    }
//}