package ru.egov66.handler

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.egov66.model.HandlerName
import ru.egov66.service.TarotCardService
import ru.egov66.util.createMessage
import kotlin.random.Random

@Component
class TarotReadingHandler(private val tarotCardService: TarotCardService) : CallbackHandler {

    override val name: HandlerName = HandlerName.TAROT_READING

    override fun processCallbackData(
        absSender: AbsSender,
        callbackQuery: CallbackQuery,
        arguments: List<String>
    ) {
        val chatId = callbackQuery.message.chatId.toString()
        val drawnCardName = tarotCardService.getTarotDeckKeys().random(Random)
        val drawnCard = tarotCardService.getCard(drawnCardName)

        drawnCard?.let {
            val messageText = "Вы вытянули карту: *${it.name}*\n\n${it.description}"
            absSender.execute(createMessage(chatId, messageText))

            // Отправка изображения карты
            val imagePath = "static/images/${it.image.substringAfter("/")}"
            val resource: Resource = ClassPathResource(imagePath)
            val imageFile = resource.file
            val photo = SendPhoto(chatId, InputFile(imageFile))
            absSender.execute(photo)
        }
    }
}
