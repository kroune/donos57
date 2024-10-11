package io.github.kroune.tg.handler

import Identifier
import alert
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.annotations.CommandHandler
import eu.vendeli.tgbot.annotations.InputHandler
import eu.vendeli.tgbot.api.chat.getChat
import eu.vendeli.tgbot.api.message.copyMessage
import eu.vendeli.tgbot.api.message.forwardMessage
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.generated.classData
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.chat.ChatType
import eu.vendeli.tgbot.types.internal.MessageUpdate
import eu.vendeli.tgbot.types.internal.getOrNull
import eu.vendeli.tgbot.types.internal.onFailure
import io.github.kroune.DANOS_GROUP
import tg.handler.BannedGuard

@CommandHandler(["/donos"], guard = BannedGuard::class)
suspend fun donos(update: MessageUpdate, bot: TelegramBot) {
    if (update.message.chat.type != ChatType.Private) {
        message { "Этот бот анонимен и не предусмотрен для работы в группах" }
        return
    }
    message { "Здравствуйте, отправьте текст вашего даноса" }.send(update.message.chat, bot)
    bot.inputListener.set(update.user, "danosInProgress")
}

@InputHandler(["danosInProgress"], guard = BannedGuard::class)
suspend fun donosInProgress(update: MessageUpdate, user: User, bot: TelegramBot) {
    if (update.message.chat.type != ChatType.Private) {
        message { "Этот бот анонимен и не предусмотрен для работы в группах" }
        return
    }
    message { "Ваш данос был отправлен в органы, спасибо за содействие" }.send(update.message.chat, bot)
    val messageCopyResult = copyMessage(update.message.chat, update.message.messageId).sendReturning(DANOS_GROUP, bot).await()
    messageCopyResult.onFailure {
        alert("sending message failed ${it.errorCode} ${it.description}}")
        return
    }
    message { "анонимный индификатор заявителя - ${Identifier.getIdentification(user.id)}" }.options {
        this.replyToMessageId = messageCopyResult.getOrNull()!!.messageId
    }.send(DANOS_GROUP, bot)
}