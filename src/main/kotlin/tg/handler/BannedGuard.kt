package tg.handler

import Identifier
import eu.vendeli.tgbot.TelegramBot
import eu.vendeli.tgbot.api.message.message
import eu.vendeli.tgbot.interfaces.helper.Guard
import eu.vendeli.tgbot.types.User
import eu.vendeli.tgbot.types.internal.MessageUpdate
import eu.vendeli.tgbot.types.internal.ProcessedUpdate

class BannedGuard : Guard {
    override suspend fun condition(
        user: User?,
        update: ProcessedUpdate,
        bot: TelegramBot
    ): Boolean {
        if (update !is MessageUpdate || user == null) {
            return false
        }
        val uuid = Identifier.getIdentification(user.id)
        if (Identifier.isBanned(uuid)) {
            message { "вы были забанены" }.send(user, bot)
            return false
        }
        return true
    }
}