package com.kraktun.kbotexample

import com.kraktun.kbot.data.Configurator
import com.kraktun.kbotexample.bots.BotsController
import com.kraktun.kbotexample.bots.ServerBot
import com.kraktun.kbotexample.bots.TestBot
import com.kraktun.kbotexample.data.DatabaseManager
import com.kraktun.kbotexample.utils.onShutdown
import com.kraktun.kbotexample.utils.onStart
import com.kraktun.kutils.log.printlnDTK
import org.telegram.telegrambots.bots.DefaultBotOptions

class Main

fun main() {
    val mainThread = Thread.currentThread()
    Runtime.getRuntime().addShutdownHook(Thread {
        onShutdown()
        try {
            mainThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    })
    onStart()
    Configurator.withDataManager(botUsername = TEST_NAME, d = DatabaseManager)
        .withDataManager(botUsername = MAIN_NAME, d = DatabaseManager)
        .withLogging { s -> printlnDTK("KBOT_LOG", s) }
    BotsController.initialize(TestBot(DefaultBotOptions()))
    BotsController.initialize(ServerBot(DefaultBotOptions()))
}
