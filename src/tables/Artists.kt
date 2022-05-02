package com.musiclibrarysusie.tables

import com.musiclibrarysusie.tables.Albums.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Artists: Table("artist") {
    val artist_id: Column<Int> = integer("album_id").autoIncrement()
    val title: Column<String> = varchar("title", 100)
    val banner: Column<String> = varchar("banner", 100)
    val description: Column<String> = varchar("description", 100)
    override val primaryKey = PrimaryKey(artist_id)
}