package com.kraktun.kbotexample.bots

import com.kraktun.kbot.utils.username
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.telegram.telegrambots.meta.TelegramBotsApi

/**
 * Maps bots username with corresponding absSender object
 */
object BotsController {

    private val bots = mutableListOf<AbsSender>()
    private val botsApi = TelegramBotsApi(DefaultBotSession::class.java)

    fun initialize(bot: LongPollingBot): BotsController {
        try {
            botsApi.registerBot(bot)
            bots.add(bot as AbsSender)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
        return this
    }

    fun getBot(name: String): AbsSender? {
        return bots.find { it.username() == name }
    }
}
