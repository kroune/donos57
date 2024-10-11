package io.github.kroune.tg.handler

import Identifier
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.InputHandler
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.MessageUpdate
import io.github.kroune.DANOS_GROUP_CHAT
import tg.handler.BannedGuard

@CommandHandler(["/ban"], guard = BannedGuard::class)
suspend fun ban(update: MessageUpdate, user: User, bot: TelegramBot) {
    if (update.message.chat.id.toString() != DANOS_GROUP_CHAT) {
        println("debug ${update.message.chat.id}")
        return
    }
    message { "отправьте индификатор заявителя" }.send(update.message.chat, bot)
    bot.inputListener.set(user) {
        "banning"
    }
    println(bot.inputListener.get(user))
}


@InputHandler(["banning"], guard = BannedGuard::class)
suspend fun banning(update: MessageUpdate, bot: TelegramBot) {
    if (update.message.chat.id.toString() != DANOS_GROUP_CHAT) {
        println("debug ${update.message.chat.id}")
        return
    }
    if (!Identifier.exists(update.text)) {
        message { "такого пользователя нет" }.send(update.message.chat, bot)
        return
    }
    Identifier.ban(update.text)
    message { "пользователь был забанен" }.send(update.message.chat, bot)
}