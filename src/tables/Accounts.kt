package com.musiclibrarysusie.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object Accounts : Table() {
    val user_id: Column<Int> = integer("user_id").autoIncrement()
    val username: Column<String> = varchar("username", 50)
    val password: Column<String> = varchar("password", 50)
    val email: Column<String> = varchar("email", 255)
    val created_on: Column<LocalDateTime> = datetime("created_on")
    val last_login: Column<LocalDateTime> = datetime("last_login")
    override val primaryKey = PrimaryKey(user_id)
}