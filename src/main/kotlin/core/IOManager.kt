import org.example.core.InputManager
import org.example.core.OutputManager

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
    fun outputLine(ob : Any) {
        output.write(ob.toString())
    }
    fun outputLine(col: Collection<Any>) {
        for(c in col) {
            output.write(c.toString())
        }
    }
}