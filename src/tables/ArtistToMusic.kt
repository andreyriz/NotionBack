package com.musiclibrarysusie.tables

import com.musiclibrarysusie.tables.Artists.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ArtistToMusic: Table() {
    val identifier: Column<Int> = integer("identifier").autoIncrement()
    val music_id: Column<Int> = reference("music_id", Musics.music_id)
    val artist_id: Column<Int> = reference("artist_id", Artists.artist_id)
    val album_id: Column<Int> = reference("album_id", Albums.album_id)
}