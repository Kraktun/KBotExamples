package com.kraktun.kbotexample.commands.pingpong

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.jobs.JobManager
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.PING_BOT_ALERT
import com.kraktun.kbotexample.services.pingpong.PingJob

/**
 * Stop ping job
 */
object StopPingCommand {
    val engine = BaseCommand(
        command = "/stopping",
        description = "Stop pinging",
        targets = mapOf(Target.USER to Status.CREATOR),
        exe = { absSender, message ->
            run {
                if (message.chatId == PING_BOT_ALERT) {
                    JobManager.removeJob(PingJob.jobInfo)
                    absSender.simpleMessage(s = "Disabled ping", c = message.chat)
                }
            }
        }
    )
}
