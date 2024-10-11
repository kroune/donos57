package tg.handler

import Identifier
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.InputHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.MessageUpdate
import io.github.kroune.DANOS_GROUP_CHAT

@CommandHandler(["/unban"], guard = BannedGuard::class)
suspend fun unban(update: MessageUpdate, user: User, bot: TelegramBot) {
    if (update.message.chat.id.toString() != DANOS_GROUP_CHAT) {
        return
    }
    message { "отправьте индификатор заявителя" }.send(update.message.chat, bot)
    bot.inputListener.set(user) {
        "unbanning"
    }
}

@InputHandler(["unbanning"], guard = BannedGuard::class)
suspend fun unbanning(update: MessageUpdate, bot: TelegramBot) {
    if (update.message.chat.id.toString() != DANOS_GROUP_CHAT) {
        return
    }
    if (!Identifier.exists(update.text)) {
        message { "такого пользователя нет" }.send(update.message.chat, bot)
        return
    }
    Identifier.unban(update.text)
    message { "пользователь был раззабанен" }.send(update.message.chat, bot)
}