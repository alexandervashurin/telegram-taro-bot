package ru.egov66.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import ru.egov66.handler.CallbackHandler
import ru.egov66.model.HandlerName
import ru.egov66.util.createMessage

@Component
class TarotBot(
    commands: Set<BotCommand>,
    callbackHandlers: Set<CallbackHandler>,
    @Value("\${telegram.token}")
    token: String,
) : TelegramLongPollingCommandBot(token) {

    @Value("\${telegram.botName}")
    private val botName: String = ""

    private lateinit var handlerMapping: Map<String, CallbackHandler>

    init {
        registerAll(*commands.toTypedArray())
        handlerMapping = callbackHandlers.associateBy { it.name.text }

        // Проверка наличия обработчика для ключа tarot
        if (HandlerName.TAROT_READING.text !in handlerMapping) {
            throw IllegalStateException("Handler for TAROT_READING is not registered.")
        }
    }

    override fun getBotUsername(): String = botName

    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
            val chatId = update.message.chatId.toString()
            if (update.message.hasText()) {
                execute(createMessage(chatId, "Вы написали: *${update.message.text}*"))
            } else {
                execute(createMessage(chatId, "Я понимаю только текст!"))
            }
        } else if (update.hasCallbackQuery()) {
            val callbackQuery = update.callbackQuery
            val callbackData = callbackQuery.data
            val callbackQueryId = callbackQuery.id
            execute(AnswerCallbackQuery(callbackQueryId))

            val callbackArguments = callbackData.split("|")
            val callbackHandlerName = callbackArguments.first()

            // Проверка наличия обработчика перед доступом
            if (callbackHandlerName in handlerMapping) {
                handlerMapping.getValue(callbackHandlerName)
                    .processCallbackData(
                        this,
                        callbackQuery,
                        callbackArguments.subList(1, callbackArguments.size)
                    )
            } else {
                // Логирование или обработка ошибки
                println("Handler for $callbackHandlerName is not registered.")
            }
        }
    }
}
