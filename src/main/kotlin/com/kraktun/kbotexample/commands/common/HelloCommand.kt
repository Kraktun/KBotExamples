package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.formattedName
import com.kraktun.kbot.utils.simpleMessage

/**
 * Simple hello command
 */
object HelloCommand {
    val engine = BaseCommand(
        command = "/hello",
        description = "Hi",
        targets = mapOf(
            Target.USER to Status.NOT_REGISTERED, // NOT_REGISTERED is the lowest acceptable status of the user, all users with a status >= NOT_REGISTERED can use the command
            Target.GROUP to Status.NOT_REGISTERED,
            Target.SUPERGROUP to Status.NOT_REGISTERED,
            Target.CHANNEL to Status.NOT_REGISTERED,
        ),
        exe = { absSender, message ->
            run {
                if (message.isChannelMessage) {
                    absSender.simpleMessage("Hello There", message.chat)
                } else {
                    absSender.simpleMessage(s = "Hello there, ${message.from.formattedName()}", c = message.chat)
                }
            }
        },
    )
}
