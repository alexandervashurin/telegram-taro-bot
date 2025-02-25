package ru.egov66.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import ru.egov66.model.TarotCard
import java.nio.file.Files

@Service
class TarotCardService(private val resourceLoader: ResourceLoader) {

    private val mapper = jacksonObjectMapper()
    private lateinit var tarotDeck: Map<String, TarotCard>

    init {
        val resource: Resource = resourceLoader.getResource("classpath:/static/cards.json")
        val jsonContent = Files.readString(resource.file.toPath())
        tarotDeck = mapper.readValue<Map<String, TarotCard>>(jsonContent).mapKeys { it.key.replace(" ", "_") }
    }

    fun getCard(name: String): TarotCard? {
        return tarotDeck[name]
    }

    fun getTarotDeckKeys(): Set<String> {
        return tarotDeck.keys
    }
}
