package com.musiclibrarysusie.tables

import com.musiclibrarysusie.tables.Musics.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Albums : Table("album") {
    val album_id: Column<Int> = integer("album_id").autoIncrement()
    val title: Column<String> = varchar("title", 100)
    val banner: Column<String> = varchar("banner", 100)
    val year: Column<Int> = integer("year_of_issue")
    override val primaryKey = PrimaryKey(album_id)
}