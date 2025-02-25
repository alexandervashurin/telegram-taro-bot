package ru.egov66.command

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.egov66.model.CommandName
import ru.egov66.model.HandlerName
import ru.egov66.util.createMessageWithInlineButtons

@Component
class TarotCommand : BotCommand(CommandName.TAROT.text, "Гадание на картах Таро") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<String>) {
        val chatId = chat.id.toString()
        val buttons = listOf(
            listOf(HandlerName.TAROT_READING.text to "Начать гадание")
        )
        absSender.execute(createMessageWithInlineButtons(chatId, "Выберите действие:", buttons))
    }
}
