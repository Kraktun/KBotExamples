package com.kraktun.kbotexample.data

import com.kraktun.kutils.log.printlnDTK
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

private const val TAG = "LOG_DB"

object BotLoggerK : SqlLogger {

    override fun log(context: StatementContext, transaction: Transaction) {
        printlnDTK(TAG, context.expandArgs(transaction))
    }
}
