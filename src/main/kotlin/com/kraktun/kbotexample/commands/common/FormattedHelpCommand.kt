package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage

/**
 * Formatted help command.
 * List all commands registered for groups.
 */
object FormattedHelpCommand {

    val engine = BaseCommand(
        command = "/fhelp",
        description = "List all commands registered for groups in the format accepted by BotFather",
        targets = mapOf(Target.USER to Status.CREATOR),
        exe = { absSender, message ->
            run {
                var text = ""
                CommandProcessor.getRegisteredCommands(absSender, Status.ADMIN, Target.GROUP).filter {
                    it.command.startsWith("/")
                }.forEach {
                    text += "${it.command.substringAfter("/")} - ${it.description}\n"
                }
                absSender.simpleMessage(text, message.chat, enableHtml = true)
            }
        }
    )
}
