package com.musiclibrarysusie.dto.MusicService

data class MusicResponse (
    val music_id: Int,
    val music_name: String,
    val music_img: String,
    val music_source: String,
    val year: Int)