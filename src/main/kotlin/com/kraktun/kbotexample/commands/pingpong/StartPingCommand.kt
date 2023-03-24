package com.kraktun.kbotexample.commands.pingpong

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.jobs.JobManager
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.PING_BOT_ALERT
import com.kraktun.kbotexample.services.pingpong.PingJob
import com.kraktun.kutils.jobs.KeyAlreadyUsedException

/**
 * Start ping job
 */
object StartPingCommand {
    val engine = BaseCommand(
        command = "/startping",
        description = "Start pinging",
        targets = mapOf(Target.USER to Status.CREATOR),
        exe = { absSender, message ->
            run {
                if (message.chatId == PING_BOT_ALERT) {
                    try {
                        JobManager.addJob(PingJob(), PingJob.jobInfo)
                        absSender.simpleMessage(s = "Enabled ping", c = message.chat)
                    } catch (e: KeyAlreadyUsedException) {
                        absSender.simpleMessage(s = "A ping job is already running", c = message.chat)
                    }
                }
            }
        },
    )
}
