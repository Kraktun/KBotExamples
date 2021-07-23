package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.*
import com.kraktun.kbotexample.data.DatabaseManager.getUserStatus

/**
 * Admin command.
 * Send keyboard with commands available for admins
 */
object AdminCommand {

    val engine = BaseCommand(
        command = "/admin",
        description = "Send keyboard with commands available for admins",
        targets = mapOf(Target.USER to Status.ADMIN),
        exe = { absSender, message ->
            run {
                val commands = mutableListOf<String>()
                CommandProcessor.getRegisteredCommands(absSender, getUserStatus(message.from, message.chat), message.chat.toEnum()).forEach {
                    commands.add(it.command)
                }
                commands.sort()
                val keyboard = getSimpleListKeyboard(list = commands, buttonsInRow = 3)
                keyboard.oneTimeKeyboard = true
                absSender.sendKeyboard(c = message.chat, s = "Here are the commands", keyboard = keyboard)
            }
        }
    )
}
