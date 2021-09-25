package com.kraktun.kbotexample.services.pingpong

import com.kraktun.kbot.bots.BotsManager
import com.kraktun.kbot.jobs.JobInfo
import com.kraktun.kbot.jobs.JobTask
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.PING_BOT_NAME
import com.kraktun.kbotexample.PING_PONG_CHAT
import kotlinx.coroutines.CoroutineScope

class PingJob : JobTask() {

    companion object {
        val jobInfo = JobInfo(
            key = "PINGJOB",
            interval = 60, // seconds
            initialDelay = 5,
            botList = listOfNotNull(BotsManager.getByUsername(PING_BOT_NAME))
        )
    }

    override fun execute(scope: CoroutineScope) {
        val absSender = jobInfo.botList[0]
        if (absSender.simpleMessage("ping", PING_PONG_CHAT) != null)
            PingController.registerPing(scope)
    }
}
