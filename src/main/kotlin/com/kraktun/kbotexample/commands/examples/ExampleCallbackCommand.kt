package com.kraktun.kbotexample.commands.examples

import com.kraktun.kbot.commands.callbacks.CallbackHolder
import com.kraktun.kbot.commands.callbacks.CallbackProcessor
import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.*
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Example on how to use callbacks
 */
object ExampleCallbackCommand {
    val engine = BaseCommand(
        command = "/callback",
        description = "Receive a message with a callback",
        targets = mapOf(Target.USER to Status.DEV),
        exe = { absSender, message ->
            run {
                val counterObject = SimpleObject(counter = 0)
                val simpleCallback = SimpleCallback(counterObject)
                val secondCallback = SecondCallback()
                val key = getSimpleInlineKeyboard(listOf(simpleCallback.button, secondCallback.button))
                absSender.sendKeyboard(s = "This is the description", c = message.chat, keyboard = key)
                CallbackProcessor.insertCallback(absSender = absSender, user = message.from.id, chat = message.chatId, callbackHolder = simpleCallback)
            }
        },
    )

    private data class SimpleObject(var counter: Int)

    private class SimpleCallback(var counterObject: SimpleObject) : CallbackHolder() {

        // unique id of the callback
        override val id = "simple_callback_id"

        // label in the button to click
        override var label = "Increment Counter"

        // time to live: after this time (in seconds) the callback can't be fired anymore
        override val ttl = 60L

        // display result as an alert rather than a toast
        override var resultAsAlert = false
        override var resultAsUrl = false

        // return a string with the text to show in the toast/alert
        override val getCallbackMessage = { _: CallbackQuery ->
            "Current counter is ${counterObject.counter}"
        }

        // increment counter every time the button is pressed
        // note that the object updated is the same as in the parent command (i.e. can be used in subsequent commands)
        override fun onCallbackFired(absSender: AbsSender, callback: CallbackQuery) {
            counterObject.counter = counterObject.counter + 1
        }

        override fun onTtlExpired(absSender: AbsSender) {
            TODO("Not yet implemented")
        }
    }

    private class SecondCallback() : CallbackHolder() {

        override val id = "second_callback_id"
        override var label = "Change to second state"
        override val ttl = 60L
        override var resultAsAlert = false
        override var resultAsUrl = false

        private enum class State {
            FIRST,
            SECOND,
        }

        // Keep track of current state, you could also use the label, but pay attention that onCallbackFired is called before getCallbackMessage
        private var currentState = State.FIRST

        override val getCallbackMessage = { _: CallbackQuery ->
            if (currentState == State.SECOND) {
                "Changed state to second"
            } else {
                "Changed state to first"
            }
        }

        override fun onCallbackFired(absSender: AbsSender, callback: CallbackQuery) {
            if (currentState == State.FIRST) {
                currentState = State.SECOND
                changeLabel(absSender, callback, "Change to first state")
            } else {
                currentState = State.FIRST
                changeLabel(absSender, callback, "Change to second state")
            }
        }

        override fun onTtlExpired(absSender: AbsSender) {
            TODO("Not yet implemented")
        }
    }
}
