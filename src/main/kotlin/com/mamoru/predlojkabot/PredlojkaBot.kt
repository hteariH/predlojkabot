package com.mamoru.predlojkabot

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.ForwardMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import javax.annotation.PostConstruct

/**
 * This example bot is an echo bot that just repeats the messages sent to him
 */
@Component
internal class PredlojkaBot(@param:Value("\${bot.token}") private val token: String,
                            @param:Value("\${bot.username}") private val username: String,
                            @param:Value("\${bot.adminid}") private val admin: String) : TelegramLongPollingBot() {

    private val logger = LoggerFactory.getLogger(PredlojkaBot::class.java)

    override fun getBotToken(): String {
        return token
    }

    override fun getBotUsername(): String {
        return username
    }

    override fun onUpdateReceived(update: Update) {

        if (update.hasMessage()) {

            val message = update.message
            try {
                logger.info("id={}", message.chat.id)
                val forwardMessage = ForwardMessage(admin, message.chatId.toString(), message.messageId)
                execute(forwardMessage)
                //                execute(new SendMessage(String.valueOf(message.getChatId()),message.getText()));
            } catch (e: TelegramApiException) {
                throw RuntimeException(e)
            }
        }
    }

    @PostConstruct
    fun start() {
        logger.info("username: {}, token: {}", username, token)
    }

//    companion object {
//        private val logger = LoggerFactory.getLogger(BaraholkaBot::class.java)
//    }
}