package com.kraktun.kbotexample

import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.UserK

const val MAIN_TOKEN = "000000000:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
const val MAIN_NAME = "my_bot"

const val TEST_TOKEN = "000000000:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
const val TEST_NAME = "my_second_bot"

val predefinedUsers = listOf(
    UserK(id = 12345, username = "@my_username", status = Status.CREATOR),
    UserK(id = 67890, username = "@another_username", status = Status.DEV)
)
