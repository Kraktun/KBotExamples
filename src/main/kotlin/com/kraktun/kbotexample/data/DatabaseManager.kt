package com.kraktun.kbotexample.data

import com.kraktun.kbot.data.DataManager
import com.kraktun.kbot.objects.GroupK
import com.kraktun.kbot.objects.GroupStatus
import com.kraktun.kbot.objects.Status
import com.kraktun.kbot.objects.UserK
import com.kraktun.kutils.log.KLogger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionManager
import org.telegram.telegrambots.meta.api.objects.User
import java.sql.Connection

object DatabaseManager : DataManager {

    private const val DATABASE_NAME = "my_database.db"

    init {
        connectDB()
    }

    private fun connectDB() {
        val dbLink = KLogger.getOutputFile().parentFile.parent + "/$DATABASE_NAME"
        val db = Database.connect("jdbc:sqlite:$dbLink", "org.sqlite.JDBC")
        db.transactionManager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE // Or Connection.TRANSACTION_READ_UNCOMMITTED

        transaction {
            addLogger(BotLoggerK)
            SchemaUtils.createMissingTablesAndColumns(Users, Groups, GroupUsers)
        }
    }

    /*
    USER MANAGEMENT
     */

    /**
     * Insert single user in DB
     */
    fun addUser(user: User, userStatus: Status, info: String? = null) {
        transaction {
            Users.insert {
                it[id] = user.id
                it[username] = user.userName
                it[status] = userStatus.name
                it[statusInfo] = info
            }
        }
    }

    /**
     * Insert list of users in DB. If already present, update its status.
     */
    fun addUser(list: List<UserK>) {
        try {
            transaction {
                Users.batchInsert(list) { user ->
                    this[Users.id] = user.id
                    this[Users.username] = user.username
                    this[Users.status] = user.status.name
                    this[Users.statusInfo] = user.userInfo
                }
            }
        } catch (e: Exception) { // if a user in the list is already present, add one at a time
            transaction {
                for (user in list) {
                    try { // Does not use addUser, so that we use a single transaction
                        Users.insert {
                            it[id] = user.id
                            it[username] = user.username
                            it[status] = user.status.name
                            it[statusInfo] = user.userInfo
                        }
                    } catch (ee: Exception) { // If user is already present, update its status
                        Users.update({ Users.id eq user.id }) {
                            it[status] = user.status.name
                        }
                    }
                }
            }
        }
    }

    /**
     * Get user from DB
     */
    fun getUser(userId: Long): UserK? {
        var userK: UserK? = null
        transaction {
            Users.select { Users.id eq userId }
                .map {
                    userK = UserK(
                        id = userId,
                        status = Status.valueOf(it[Users.status].uppercase()),
                        username = it[Users.username],
                        userInfo = it[Users.statusInfo]
                    )
                }
        }
        return userK
    }

    override fun getUserStatus(userId: Long): Status {
        return getUser(userId)?.status ?: Status.NOT_REGISTERED
    }

    /*
    GROUP MANAGEMENT
     */

    /**
     * Return true if group exists inside the DB
     */
    fun groupExists(groupId: Long): Boolean {
        var result = false
        transaction {
            result = Groups.select { Groups.id eq groupId }.count() > 0
        }
        return result
    }

    /**
     * Insert group in DB
     */
    fun addGroup(groupId: Long) {
        transaction {
            Groups.insert {
                it[id] = groupId
                it[status] = GroupStatus.NORMAL.name
            }
        }
    }

    /**
     * Get group and all the members with a custom status
     */
    fun getGroup(groupId: Long): GroupK? {
        var groupK: GroupK? = null
        val users: ArrayList<UserK> = arrayListOf()
        transaction {
            GroupUsers.select { GroupUsers.group eq groupId }
                .forEach {
                    users.add(
                        UserK(
                            id = it[GroupUsers.user],
                            status = Status.valueOf(it[GroupUsers.status].uppercase())
                        )
                    )
                }
        }
        if (users.size > 0)
            groupK = GroupK(id = groupId, users = users, status = getGroupStatus(groupId))
        return groupK
    }

    /**
     * Get group status
     */
    override fun getGroupStatus(groupId: Long): GroupStatus {
        var statusK = GroupStatus.NORMAL
        transaction {
            Groups.select { Groups.id eq groupId }
                .map {
                    statusK = GroupStatus.valueOf(it[Groups.status].uppercase())
                }
        }
        return statusK
    }

    /**
     * Update status of a group
     */
    fun updateGroup(groupId: Long, newStatus: GroupStatus) {
        transaction {
            Groups.update({ Groups.id eq groupId }) {
                it[status] = newStatus.name
            }
        }
    }

    /*
    GROUP USER MANAGEMENT
     */

    /**
     * Add users to group with defined status
     * If already present, update status
     */
    fun addGroupUsers(groupId: Long, usersId: List<Long>, statusK: Status) {
        try {
            transaction {
                GroupUsers.batchInsert(usersId) { userId ->
                    this[GroupUsers.group] = groupId
                    this[GroupUsers.user] = userId
                    this[GroupUsers.status] = statusK.name
                }
            }
        } catch (e: Exception) { // if a user in the list is already present, add one at a time
            transaction {
                for (userId in usersId) { // Does not use addGroupUser, so that we use a single transaction
                    try {
                        GroupUsers.insert {
                            it[group] = groupId
                            it[user] = userId
                            it[status] = statusK.name
                        }
                    } catch (ee: Exception) {
                        GroupUsers.update({ GroupUsers.group eq groupId and (GroupUsers.user eq userId) }) {
                            it[status] = statusK.name
                        }
                    }
                }
            }
        }
    }

    /**
     * Get user status in a group
     */
    override fun getGroupUserStatus(groupId: Long, userId: Long): Status {
        var statusK: Status = Status.NOT_REGISTERED
        transaction {
            GroupUsers.select { GroupUsers.group eq groupId and (GroupUsers.user eq userId) }
                .map {
                    statusK = Status.valueOf(it[GroupUsers.status].uppercase())
                }
        }
        return statusK
    }

    /**
     * Update status for user in group
     */
    fun updateGroupUser(groupId: Long, userId: Long, newStatus: Status) {
        transaction {
            GroupUsers.update({ GroupUsers.group eq groupId and (GroupUsers.user eq userId) }) {
                it[status] = newStatus.name
            }
        }
    }

    /**
     * Update status of users in a group whose status matches oldStatus
     */
    fun updateGroupUsersStatus(groupId: Long, oldStatus: Status, newStatus: Status) {
        transaction {
            GroupUsers.update({ GroupUsers.group eq groupId and (GroupUsers.status eq oldStatus.name) }) {
                it[status] = newStatus.name
            }
        }
    }
}
