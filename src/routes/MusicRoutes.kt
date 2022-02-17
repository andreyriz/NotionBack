package com.musiclibrarysusie.routes

import com.musiclibrarysusie.dto.MusicService.MusicRequest
import com.musiclibrarysusie.dto.MusicService.MusicResponse
import com.musiclibrarysusie.tables.Musics
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.musicRouting() {
    route("/music") {

        post {
            val musicRequest = call.receive<MusicRequest>()
            var response: MusicResponse? = null

            transaction {
                Musics.select {
                    Musics.music_id eq musicRequest.id
                }.limit(1).firstOrNull()?.let {
                    response = MusicResponse(
                        music_id = it[Musics.music_id],
                        music_name = it[Musics.music_name],
                        music_img = it[Musics.music_img],
                        music_source = it[Musics.music_source],
                        year = it[Musics.year]
                    )
                }
            }

            response?.let { it1 -> call.respond(it1) }
        }

    }
}

fun Application.registerMusicRoutes() {
    routing {
        musicRouting()
    }
}