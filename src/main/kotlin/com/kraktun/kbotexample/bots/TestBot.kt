package com.kraktun.kbotexample.bots

import com.kraktun.kbot.bots.SimpleLongPollingBot
import com.kraktun.kbot.utils.formattedName
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.TEST_NAME
import com.kraktun.kbotexample.TEST_TOKEN
import com.kraktun.kbotexample.commands.common.HelloCommand

/**
 * Main class: register the commands and process non-command updates
 */
class TestBot : SimpleLongPollingBot(TEST_NAME, TEST_TOKEN) {

    /**
     * Register Commands
     */
    init {
        this.withCommand(HelloCommand.engine)
            .onNewUser { up ->
                val welcomeU = up.message.newChatMembers.map {
                    it.formattedName() // i.e. '@username' or 'first_name last_name' or only 'first_name'
                }.reduce { acc, sing ->
                    "$acc $sing," // single message for multiple new users
                }
                simpleMessage("Welcome $welcomeU", up.message.chatId)
            }.onElse { up ->
                if (up.hasMessage() && up.message.chat.isUserChat && up.message.hasText()) {
                    simpleMessage("I'm a parrot\n ${up.message.text}", up.message.chatId)
                }
            }.register()
    }
}
