package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbot.utils.toEnum
import com.kraktun.kbotexample.data.DatabaseManager.getUserStatus

/**
 * Help command.
 * List all commands registered.
 */
object HelpCommand {

    val engine = BaseCommand(
        command = "/help",
        description = "Show a list of commands",
        targets = mapOf(
            Target.USER to Status.USER,
            Target.GROUP to Status.NOT_REGISTERED,
            Target.SUPERGROUP to Status.NOT_REGISTERED,
        ),
        exe = { absSender, message ->
            run {
                var text = "<b>Here is a list of all the commands</b>:\n"
                CommandProcessor.getRegisteredCommands(absSender, getUserStatus(message.from, message.chat), message.chat.toEnum()).forEach {
                    text += "${it.command} : ${it.description}\n"
                }
                absSender.simpleMessage(text, message.chat, enableHtml = true)
            }
        },
    )
}
