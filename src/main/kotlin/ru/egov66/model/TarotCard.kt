package ru.egov66.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TarotCard(
    @JsonProperty("image") val image: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("name") val name: String  // Добавьте это свойство
)
