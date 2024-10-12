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
    message { "Этот бот создан для защиты всех добрых  людей от клеветников, подговорщиков, зачинщиков смуты и других противников Николая Гусева-Лебедева. Чтобы начать, введите команду /donos" }.send(
        update.message.chat,
        bot
    )
}
