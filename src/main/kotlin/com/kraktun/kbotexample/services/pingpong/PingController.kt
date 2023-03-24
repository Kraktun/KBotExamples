package com.kraktun.kbotexample.services.pingpong

import kotlinx.coroutines.*

object PingController {

    private lateinit var listener: PingListener

    @Volatile
    private var pingHolder = mutableListOf<Long>()
    private const val waitingTime = 30L // 30 secs
    private var counter = 0L

    fun registerListener(listener: PingListener) {
        PingController.listener = listener
    }

    fun registerPing(scope: CoroutineScope) {
        scope.launch {
            var currentPing: Long
            // add a ping
            synchronized(this) {
                if (counter == Long.MAX_VALUE - 1) {
                    counter = -1L
                }
                counter++
                currentPing = counter
                pingHolder.add(currentPing)
            }
            delay(waitingTime * 1000L)

            if (isActive) {
                // check if the previous ping is still there
                synchronized(this) {
                    if (pingHolder.size > 0) {
                        // check that the first ping is the one we added before
                        if (pingHolder.first() == currentPing) {
                            pingHolder.removeAt(0)
                            listener.onPongTimeExceeded()
                        } // else do nothing
                    }
                }
            }
        }
    }

    fun registerPong() {
        synchronized(this) {
            if (pingHolder.size > 0) {
                pingHolder.removeAt(0)
            }
        }
    }
}
