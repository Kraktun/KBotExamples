package com.kraktun.kbotexample

import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.UserK

const val MAIN_TOKEN = "000000000:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
const val MAIN_NAME = "my_bot"

const val TEST_TOKEN = "000000000:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
const val TEST_NAME = "my_second_bot"

const val PING_BOT_TOKEN = "000000000:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
const val PING_BOT_NAME = "my_ping_bot"
const val PING_BOT_ALERT = 12345L
const val PING_PONG_CHAT = -1234567L
const val PING = "ping"

const val PONG_BOT_TOKEN = "000000000:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
const val PONG_BOT_NAME = "my_pong_bot"
const val PONG = "pong"

val predefinedUsers = listOf(
    UserK(id = 12345, username = "@my_username", status = Status.CREATOR),
    UserK(id = 67890, username = "@another_username", status = Status.DEV)
)
