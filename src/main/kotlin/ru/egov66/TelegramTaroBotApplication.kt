package ru.egov66

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TelegramTaroBotApplication

fun main(args: Array<String>) {
	runApplication<TelegramTaroBotApplication>(*args)
}
