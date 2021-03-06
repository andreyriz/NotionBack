package com.musiclibrarysusie.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Musics : Table("music") {
    val music_id: Column<Int> = integer("music_id").autoIncrement()
    val music_name: Column<String> = varchar("music_name", 100)
    val music_img: Column<String> = varchar("banner", 100)
    val music_source: Column<String> = varchar("source", 100)
    val year: Column<Int> = integer("year_of_issue")
    override val primaryKey = PrimaryKey(music_id)
}
