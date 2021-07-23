package com.kraktun.kbotexample.data

import org.jetbrains.exposed.sql.*

// https://www.kotlinresources.com/library/exposed/
// DSL

object Users : Table() {
    val id = long("id")
    val username = text("username").nullable()
    val status = text("status")
    val statusInfo = text("status_info").nullable()
    val bannedUntil = long("bannedUntil").default(-1L)
    override val primaryKey = PrimaryKey(id)
}

object Groups : Table() {
    val id = long("id")
    val status = text("status")
    val bannedUntil = long("bannedUntil").default(-1L)
    override val primaryKey = PrimaryKey(id)
}

/*
A user in a group does not need to start the bot in a private chat
 */
object GroupUsers : Table() {
    val group = reference("group", Groups.id, onDelete = ReferenceOption.CASCADE)
    val user = long("user")
    val status = text("status")
    val statusInfo = text("status_info").nullable()
    val bannedUntil = long("bannedUntil").default(-1L)
    override val primaryKey = PrimaryKey(group, user)
}
