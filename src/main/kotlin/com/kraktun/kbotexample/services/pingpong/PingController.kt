package com.kraktun.kbotexample.services.pingpong

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Object that creates coroutines every time it receives a ping and checks if we received a pong in the next 60 seconds, otherwise fires an event.
 */
object PingController {

    private lateinit var listener: PingListener
    @Volatile
    private var pingHolder = mutableListOf<Long>()
    private const val waitingTime = 60000L // 60 secs
    private var counter = 0L

    fun registerListener(listener: PingListener) {
        PingController.listener = listener
    }

    fun registerPing() {
        runBlocking {
            launch {
                // add a ping
                var currentPing: Long
                synchronized(this) {
                    counter++
                    currentPing = counter
                    pingHolder.add(currentPing)
                }
                // wait
                delay(waitingTime)
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
