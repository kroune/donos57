import eu.vendeli.tgbot.TelegramBot
import kotlinx.coroutines.runBlocking

val bot = TelegramBot("7868201713:AAEfn1w9Q27DEprFynACe7_V5vz4joeYdtA")
fun main() {
    runBlocking {
        bot.handleUpdates()
    }
}