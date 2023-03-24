package com.kraktun.kbotexample.commands.misc

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.utils.ifNotNull
import com.kraktun.kbotexample.utils.runCommand

/**
 * Execute a shell command
 */
object ShellCommand {

    val engine = BaseCommand(
        command = "/shell",
        description = "Execute a shell command",
        targets = mapOf(
            Target.USER to Status.CREATOR,
        ),
        exe = { absSender, message ->
            run {
                listOf("ls", "-la").runCommand(
                    onFailure = {
                        absSender.simpleMessage(s = "Failed with result:\n<b>${it.message}</b>", c = message.chat, enableHtml = true)
                    },
                ).ifNotNull {
                    absSender.simpleMessage(s = "Command sent with result:\n<b>$it</b>", c = message.chat, enableHtml = true)
                    it
                }
            }
        },
    )
}
