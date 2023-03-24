package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.arguments
import com.kraktun.kbot.utils.simpleMessage

/**
 * Formatted help command.
 * List all commands registered for groups.
 */
object FormattedHelpCommand {

    val engine = BaseCommand(
        command = "/fhelp",
        description = "List all commands registered for groups in the format accepted by BotFather. Send /fhelp for the options.",
        targets = mapOf(Target.USER to Status.CREATOR),
        exe = { absSender, message ->
            run {
                val text = StringBuilder()
                val args = message.arguments()
                if (args.size == 2) {
                    // params have been passed
                    val p = args[0]
                    val flagUser = p.contains('u', ignoreCase = true)
                    val flagGroup = p.contains('g', ignoreCase = true)
                    val flagChannel = p.contains('c', ignoreCase = true)
                    val status = when (args[1]) {
                        "creator" -> Status.CREATOR
                        "dev" -> Status.DEV
                        "admin" -> Status.ADMIN
                        "power" -> Status.POWER_USER
                        "user" -> Status.USER
                        "all" -> Status.NOT_REGISTERED
                        else -> {
                            absSender.simpleMessage("Invalid options", message.chat, enableHtml = true)
                            return@run
                        }
                    }
                    val allCommands = mutableSetOf<String>()
                    if (flagUser) {
                        allCommands.addAll(
                            CommandProcessor.getRegisteredCommands(absSender, status, Target.USER).filter {
                                it.command.startsWith("/")
                            }.map {
                                "${it.command.substringAfter("/")} - ${it.description}\n"
                            },
                        )
                    }
                    if (flagGroup) {
                        allCommands.addAll(
                            CommandProcessor.getRegisteredCommands(absSender, status, Target.GROUP).filter {
                                it.command.startsWith("/")
                            }.map {
                                "${it.command.substringAfter("/")} - ${it.description}\n"
                            },
                        )
                    }
                    if (flagChannel) {
                        allCommands.addAll(
                            CommandProcessor.getRegisteredCommands(absSender, status, Target.CHANNEL).filter {
                                it.command.startsWith("/")
                            }.map {
                                "${it.command.substringAfter("/")} - ${it.description}\n"
                            },
                        )
                    }
                    allCommands.sorted().forEach {
                        text.append(it)
                    }
                    absSender.simpleMessage(text.toString(), message.chat, enableHtml = true)
                } else {
                    absSender.simpleMessage(
                        "Use as follow:\n<b>/fhelp ugc admin</b>\n\nwith <b>u</b> to include user commands" +
                            "\n<b>g</b> to include group commands" +
                            "\n<b>c</b> to include channel commands" +
                            "\nThen one status among [creator, dev, admin, power, user, all]",
                        message.chat,
                        enableHtml = true,
                    )
                }
            }
        },
    )
}
