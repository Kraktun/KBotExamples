package com.kraktun.kbotexample.bots

import com.kraktun.kbot.bots.SimpleLongPollingBot
import com.kraktun.kbot.utils.messageOrPost
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.*
import com.kraktun.kbotexample.commands.common.HelloCommand

/**
 * Main class: register the commands and process non-command updates
 */
class PongBot : SimpleLongPollingBot(PONG_BOT_NAME, PONG_BOT_TOKEN) {

    /**
     * No commands to register, it just needs to reply 'pong' if it receives 'ping'
     */
    init {
        this.withCommand(HelloCommand.engine)
            .onElse { up ->
                val message = up.messageOrPost()
                if (message != null && message.hasText() && message.chat.id == PING_PONG_CHAT && message.text.equals(PING, ignoreCase = true)) {
                    this.simpleMessage(PONG, message.chat)
                }
            }.register()
    }
}
