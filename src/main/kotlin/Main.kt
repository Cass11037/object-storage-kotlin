package org.example

fun main() {
    fun parseInputAdvanced(input: String): Pair<String, List<String>> {
        val regex = """("[^"]*"|'[^']*'|\S+)""".toRegex()
        val matches = regex.findAll(input)
        val parts = matches.map { it.value.trim('\'', '"') }.toList()

        return if (parts.isEmpty()) {
            "" to emptyList()
        } else {
            parts[0] to parts.drop(1)
        }
    }
    val (cmd, args) = parseInputAdvanced("update 42 'Tesla Model X' 150.5")
    println(cmd)
    println(args)
}