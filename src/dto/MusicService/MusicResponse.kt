package com.musiclibrarysusie.dto.MusicService

data class MusicResponse (val music:Music)

data class Music (
    val music_id: Int,
    val music_name: String,
    val music_img: String,
    val music_source: String,
    val year: Int)