package com.kraktun.kbotexample.commands.pingpong

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.jobs.JobManager
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.arguments
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.PING_BOT_ALERT
import com.kraktun.kbotexample.services.pingpong.PingJob
import com.kraktun.kutils.jobs.KeyAlreadyUsedException

/**
 * Set ping job interval
 */
object SetPingIntervalCommand {
    val engine = BaseCommand(
        command = "/interval",
        description = "Set interval for ping messages in seconds",
        targets = mapOf(Target.USER to Status.CREATOR),
        argsNum = 1,
        exe = { absSender, message ->
            run {
                if (message.chatId == PING_BOT_ALERT) {
                    try {
                        val newInterval = message.arguments()[0].toLong()
                        val newJob = PingJob.jobInfo
                        newJob.interval = newInterval
                        // remove previous job (we don't care if it was already using a custom interval as we consider only the key)
                        JobManager.removeJob(PingJob.jobInfo)
                        // add the new job
                        JobManager.addJob(PingJob(), newJob)
                        absSender.simpleMessage(s = "Enabled ping job with interval $newInterval", c = message.chat)
                    } catch (e: KeyAlreadyUsedException) {
                        absSender.simpleMessage(s = "A ping job is already running", c = message.chat)
                    } catch (e: NumberFormatException) {
                        absSender.simpleMessage(s = "Invalid parameter passed", c = message.chat)
                    } catch (e: IndexOutOfBoundsException) {
                        absSender.simpleMessage(s = "No parameters passed", c = message.chat)
                    }
                }
            }
        },
    )
}
