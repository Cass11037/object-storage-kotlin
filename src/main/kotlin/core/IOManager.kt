import org.example.core.*

class IOManager(
    private val input: InputManager,
    private val output: OutputManager
) {
    fun readWithPrompt(prompt: String): String? {
        output.write(prompt)
        return input.readLine()
    }
    fun outputInformation(prompt: String){
        output.write(prompt)
    }
}