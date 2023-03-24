package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage

/**
 * Simple command to get chat id
 */
object InfoCommand {

    val engine = BaseCommand(
        command = "/info",
        description = "Get chat info",
        targets = mapOf(
            Target.USER to Status.ADMIN,
            Target.GROUP to Status.ADMIN,
            Target.SUPERGROUP to Status.ADMIN,
            Target.CHANNEL to Status.NOT_REGISTERED,
        ),
        exe = { absSender, message ->
            run {
                absSender.simpleMessage(s = "The chat id is: ${message.chatId}", c = message.chat)
            }
        },
    )
}
