package org.example.tests

import io.mockk.*
import org.example.commands.Command
import org.example.core.CommandProcessor
import org.junit.jupiter.api.Test
import java.util.*

class CommandProcessorTest {

    @Test
    fun `processCommand should execute a valid command`() {
        val mockCommand = mockk<Command> {
            every { getName() } returns "test" // Добавляем ответ для getName()
        }
        val commands = mapOf("test" to mockCommand)
        val scanner = Scanner("test arg1 arg2 \n exit")
        val commandProcessor = CommandProcessor(commands, scanner, "test.csv")
        every { mockCommand.execute(any(), any()) } just Runs
        commandProcessor.start()
        verify {
            mockCommand.execute(listOf("arg1", "arg2"), commandProcessor.collectionManager)
        }
    }

    @Test
    fun `processCommand should not execute an unvalid command`() {
        val mockCommand = mockk<Command> {
            every { getName() } returns "test" // Добавляем ответ для getName()
        }
        val commands = mapOf("test" to mockCommand)
        val scanner = Scanner("test arg1 arg2 \n exit")
        val commandProcessor = CommandProcessor(commands, scanner, "test.csv")
        every { mockCommand.execute(any(), any()) } just Runs
        commandProcessor.start()
        verify {
            mockCommand.execute(listOf("arg1", "arg2"), commandProcessor.collectionManager)
        }
    }
}