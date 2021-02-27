package com.kraktun.kbotexample.bots

import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.update.digest
import com.kraktun.kbot.utils.formattedName
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.TEST_NAME
import com.kraktun.kbotexample.TEST_TOKEN
import com.kraktun.kbotexample.commands.common.HelloCommand
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * Main class: register the commands and process non-command updates
 */
class TestBot(options: DefaultBotOptions) : TelegramLongPollingBot(options) {

    /**
     * Return username of the bot
     */
    override fun getBotUsername(): String {
        return TEST_NAME
    }

    /**
     * Return token of the bot
     */
    override fun getBotToken(): String {
        return TEST_TOKEN
    }

    /**
     * Register Commands
     */
    init {
        CommandProcessor.registerCommand(botUsername, HelloCommand().engine)
    }

    /**
     * On update: fire commands if it's a recognized command or is part of a ask-answer command, else manage in a different way
     */
    override fun onUpdateReceived(update: Update) {
        digest(update,
            onNewUser = { up ->
                val welcomeU = up.message.newChatMembers.map {
                    it.formattedName() // i.e. '@username' or 'first_name last_name' or only 'first_name'
                }.reduce { acc, sing ->
                    "$acc $sing," // single message for multiple new users
                }
                simpleMessage("Welcome $welcomeU", up.message.chatId)
            },
            onElse = { up ->
                if (up.hasMessage() && up.message.chat.isUserChat && up.message.hasText()) {
                    simpleMessage("I'm a parrot\n ${up.message.text}", up.message.chatId)
                }
            }
        )
    }
}
