package tg.handler

import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.chat.ChatType
import eu.vendeli.tgbot.types.internal.MessageUpdate

@CommandHandler(["/info", "/start", "/help"], guard = BannedGuard::class)
suspend fun info(update: MessageUpdate, bot: TelegramBot) {
    if (update.message.chat.type != ChatType.Private) {
        message { "Этот бот анонимен и не предусмотрен для работы в группах" }
        return
    }
    message { "Этот бот позволяет писать анонимные даносы. Отправьте /danos для создания даноса" }.send(
        update.message.chat,
        bot
    )
}