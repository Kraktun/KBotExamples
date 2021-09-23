package com.kraktun.kbotexample.bots

import com.kraktun.kbot.bots.SimpleLongPollingBot
import com.kraktun.kbot.jobs.JobManager
import com.kraktun.kbot.utils.messageOrPost
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.*
import com.kraktun.kbotexample.commands.common.FormattedHelpCommand
import com.kraktun.kbotexample.commands.common.HelpCommand
import com.kraktun.kbotexample.commands.common.StartCommand
import com.kraktun.kbotexample.commands.pingpong.SetPingIntervalCommand
import com.kraktun.kbotexample.commands.pingpong.StartPingCommand
import com.kraktun.kbotexample.commands.pingpong.StopPingCommand
import com.kraktun.kbotexample.services.pingpong.PingController
import com.kraktun.kbotexample.services.pingpong.PingJob
import com.kraktun.kbotexample.services.pingpong.PingListener

/**
 * How to use this:
 * Register PingBot and PongBot in two different jar.
 * Add both bots to a channel as admins.
 * Use /startping from a user chat with PingBot.
 */
class PingBot : SimpleLongPollingBot(PING_BOT_NAME, PING_BOT_TOKEN), PingListener {

    /**
     * Register Commands
     */
    init {
        this.withCommand(StartPingCommand.engine)
            .withCommand(StopPingCommand.engine)
            .withCommand(StartCommand.engine)
            .withCommand(HelpCommand.engine)
            .withCommand(FormattedHelpCommand.engine)
            .withCommand(SetPingIntervalCommand.engine)
            .onElse {
                val message = it.messageOrPost()
                if (message != null && message.hasText() && message.chat.id == PING_PONG_CHAT && message.text.equals(PONG, ignoreCase = true))
                    PingController.registerPong()
            }
            .register()
        PingController.registerListener(this)
        simpleMessage("Initiated boot sequence. Ping service is disabled.", PING_BOT_ALERT)
    }

    override fun onPongTimeExceeded() {
        simpleMessage("Ping time exceeded. Disabled job.", PING_BOT_ALERT)
        JobManager.removeJob(PingJob.jobInfo)
    }
}
