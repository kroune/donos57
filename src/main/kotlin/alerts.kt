import eu.vendeli.tgbot.api.message.message
import io.github.kroune.ADMIN_TG_CHAT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun alert(message: String): Job {
    return CoroutineScope(Dispatchers.IO).launch {
        message { message }.send(ADMIN_TG_CHAT_ID, bot)
    }
}
