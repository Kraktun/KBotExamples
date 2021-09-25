package com.kraktun.kbotexample.utils

import com.kraktun.kbot.data.Configurator
import com.kraktun.kbotexample.data.DatabaseManager
import com.kraktun.kbotexample.predefinedUsers
import com.kraktun.kutils.file.BuildEnv
import com.kraktun.kutils.log.KLogger
import com.kraktun.kutils.log.LogFolder
import com.kraktun.kutils.log.printlnDTK
import com.kraktun.kutils.time.TimeFormat
import java.util.concurrent.TimeUnit

private const val TAG = "MANAGE_HOOKS"
/**
 * What to execute on start
 */
fun onStart() {

    KLogger.initialize(
        c = com.kraktun.kbotexample.Main::class.java,
        type = LogFolder.DEFAULT,
        pattern = TimeFormat.YMD,
        logFolder = "logs",
        buildEnv = BuildEnv.INTELLIJ
    ).withExecutor(
        interval = 120,
        unit = TimeUnit.SECONDS
    )
    printlnDTK(TAG, "Starting system")
    printlnDTK(TAG, "Current version is: ${com.kraktun.kbotexample.Main::class.java.getPackage().implementationVersion}")
    // Insert predefined users
    printlnDTK(TAG, "Adding predefined users")
    DatabaseManager.addUser(predefinedUsers)
    printlnDTK(TAG, "Predefined users added")
}

/**
 * What to execute when closing
 */
fun onShutdown() {
    printlnDTK(TAG, "Closing system")
    Configurator.shutdown()
    KLogger.close()
    Thread.sleep(2000)
}
