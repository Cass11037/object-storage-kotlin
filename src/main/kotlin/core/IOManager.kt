import org.example.core.*

class IOManager(
    private val input: InputManager,
    private val output: OutputManager
) {
    fun readWithPrompt(prompt: String): String? {
        output.write(prompt)
        return input.readLine()
    }
    fun outputLine(prompt: String){
        output.write(prompt+'\n')
    }
    fun outputInline(prompt: String){
        output.write(prompt)
    }
    fun readLine():String{
        return input.readLine() ?: ""
    }
}