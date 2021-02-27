package com.kraktun.kbotexample.bots

import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.update.digest
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.MAIN_NAME
import com.kraktun.kbotexample.MAIN_TOKEN
import com.kraktun.kbotexample.commands.common.*
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * Main class: register the commands and process non-command updates
 */
class ServerBot(options: DefaultBotOptions) : TelegramLongPollingBot(options) {

    /**
     * Return username of the bot
     */
    override fun getBotUsername(): String {
        return MAIN_NAME
    }

    /**
     * Return token of the bot
     */
    override fun getBotToken(): String {
        return MAIN_TOKEN
    }

    /**
     * Register Commands
     */
    init {
        CommandProcessor.registerCommand(botUsername, InfoCommand().engine)
        CommandProcessor.registerCommand(botUsername, HelloCommand().engine)
        CommandProcessor.registerCommand(botUsername, StartCommand().engine)
        CommandProcessor.registerCommand(botUsername, HelpCommand().engine)
        CommandProcessor.registerCommand(botUsername, AdminCommand().engine)
        CommandProcessor.registerCommand(botUsername, FormattedHelpCommand().engine)
    }

    /**
     * On update: fire commands if it's a recognized command or if it's part of a ask-answer command, else reply with the sent message
     */
    override fun onUpdateReceived(update: Update) {
        digest(update,
            onElse = { up ->
                if (up.hasMessage() && up.message.chat.isUserChat && up.message.hasText()) {
                    simpleMessage("I'm a parrot\n ${up.message.text}", up.message.chatId)
                }
            }
        )
    }
}
