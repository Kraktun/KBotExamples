package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.CommandInterface
import com.kraktun.kbot.commands.core.CommandProcessor
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.*
import com.kraktun.kbotexample.data.DatabaseManager.getUserStatus
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Admin command.
 * Send keyboard with commands available for admins
 */
class AdminCommand : CommandInterface {

    val engine = BaseCommand(
        command = "/admin",
        description = "Send keyboard with commands available for admins",
        targets = mapOf(Target.USER to Status.ADMIN),
        exe = this
    )

    override fun execute(absSender: AbsSender, message: Message) {
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
