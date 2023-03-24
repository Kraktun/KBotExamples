package com.kraktun.kbotexample.utils

import java.io.File
import java.util.concurrent.TimeUnit

fun List<String>.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
    onFailure: (t: Throwable) -> Unit = { it.printStackTrace() },
): String? {
    return runCatching {
        ProcessBuilder(this)
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().also { it.waitFor(timeoutAmount, timeoutUnit) }
            .inputStream.bufferedReader().readText()
    }.onFailure { onFailure(it) }.getOrNull()
}

fun <T> T?.ifNotNull(func: (T) -> T?): T? {
    return if (this != null) {
        func(this)
    } else {
        return null
    }
}

fun <T> T?.ifNull(func: () -> T?): T? {
    return if (this == null) {
        func()
    } else {
        return null
    }
}
