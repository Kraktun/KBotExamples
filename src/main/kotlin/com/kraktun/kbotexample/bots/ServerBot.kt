package com.kraktun.kbotexample.bots

import com.kraktun.kbot.bots.SimpleLongPollingBot
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.MAIN_NAME
import com.kraktun.kbotexample.MAIN_TOKEN
import com.kraktun.kbotexample.commands.common.*

/**
 * Main class: register the commands and process non-command updates
 */
class ServerBot : SimpleLongPollingBot(MAIN_NAME, MAIN_TOKEN) {

    /**
     * Register Commands
     */
    init {
        this.withCommand(InfoCommand.engine)
            .withCommand(HelloCommand.engine)
            .withCommand(StartCommand.engine)
            .withCommand(HelpCommand.engine)
            .withCommand(AdminCommand.engine)
            .withCommand(FormattedHelpCommand.engine)
            .onElse { up ->
                if (up.hasMessage() && up.message.chat.isUserChat && up.message.hasText()) {
                    simpleMessage("I'm a parrot\n ${up.message.text}", up.message.chatId)
                }
            }.register()
    }
}
