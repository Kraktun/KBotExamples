package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.CommandInterface
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Simple command
 */
class InfoCommand : CommandInterface {

    val engine = BaseCommand(
        command = "/info",
        description = "Get chat info",
        targets = mapOf(Target.USER to Status.ADMIN,
            Target.GROUP to Status.ADMIN,
            Target.SUPERGROUP to Status.ADMIN,
            Target.CHANNEL to Status.NOT_REGISTERED),
        exe = this
    )

    override fun execute(absSender: AbsSender, message: Message) {
        absSender.simpleMessage(s = "The chat id is: ${message.chatId}", c = message.chat)
    }
}
