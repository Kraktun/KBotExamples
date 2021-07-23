package com.kraktun.kbotexample.commands.common

import com.kraktun.kbot.commands.core.BaseCommand
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.Target
import com.kraktun.kbot.utils.isGroupOrSuper
import com.kraktun.kbot.utils.simpleMessage
import com.kraktun.kbotexample.data.DatabaseManager
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

/**
 * Start command: adds the user/group to the DB and adds admins.
 */
object StartCommand {

    val engine = BaseCommand(
        command = "/start",
        description = "Start the bot. If used in a group, add/reset admins.",
        targets = mapOf(
            Target.USER to Status.NOT_REGISTERED,
            Target.GROUP to Status.NOT_REGISTERED,
            Target.SUPERGROUP to Status.NOT_REGISTERED
        ),
        exe = { absSender, message ->
            run {
                val chatId = message.chatId
                if (message.chat.isUserChat && DatabaseManager.getUser(message.from.id) == null) // Add only if not present, or it will overwrite current value
                    DatabaseManager.addUser(user = message.from, userStatus = Status.USER)
                else if (message.chat.isGroupOrSuper()) {
                    // If it's a group insert the group and add the admins as admin
                    if (!DatabaseManager.groupExists(chatId)) {
                        DatabaseManager.addGroup(chatId)
                    } else {
                        // Reset old admins
                        DatabaseManager.updateGroupUsersStatus(groupId = chatId, oldStatus = Status.ADMIN, newStatus = Status.USER)
                    }
                    val getAdmins = GetChatAdministrators()
                    getAdmins.chatId = chatId.toString()
                    try {
                        val admins = absSender.execute(getAdmins)
                        DatabaseManager.addGroupUsers(groupId = chatId, usersId = admins.map { admin -> (admin as ChatMemberAdministrator).user.id }, statusK = Status.ADMIN)
                    } catch (e: TelegramApiException) {
                        absSender.simpleMessage("An error occurred: ${e.message}", chatId)
                        e.printStackTrace()
                    }
                }
                absSender.simpleMessage("Welcome", chatId)
            }
        }
    )
}
