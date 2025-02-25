package ru.egov66.command

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.egov66.model.CommandName
import ru.egov66.util.createMessageWithSimpleButtons

@Component
class ButtonsCommand : BotCommand(CommandName.BUTTONS.text, "") {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        absSender.execute(
            createMessageWithSimpleButtons(
                chat.id.toString(),
                "Нажмите на одну из кнопок.",
                listOf(
                    listOf("Кнопка 1", "Кнопка 2"),
                    listOf("Кнопка 3", "Кнопка 4"),
                )
            )
        )
    }
}