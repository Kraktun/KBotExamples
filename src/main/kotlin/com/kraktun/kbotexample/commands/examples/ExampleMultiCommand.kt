package com.kraktun.kbotexample.commands.examples
import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.commands.core.MultiCommandInterface
import com.kraktun.kbot.commands.core.MultiCommandsHandler
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.*
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Example of a chain of commands
 */
object ExampleMultiCommand {
    val engine = BaseCommand(
        command = "/multicommand",
        description = "Example of ask-answer command",
        targets = mapOf(Target.USER to Status.DEV),
        exe = { absSender, message ->
            run {
                absSender.simpleMessage(s = "This is the first message received: ${message.text}", c = message.chat)
                MultiCommandsHandler.insertCommand(absSender = absSender, user = message.from, chat = message.chat, command = SecondStep(), data = message.text)
            }
        }
    )

    private class SecondStep : MultiCommandInterface {
        override suspend fun executeAfter(absSender: AbsSender, message: Message, data: Any?) {
            val previousMessage = data as String
            absSender.simpleMessage(s = "This is the second message received: ${message.text}.\nYour previous message was: $previousMessage", c = message.chat)
            // add a third step if necessary
        }
    }
}
