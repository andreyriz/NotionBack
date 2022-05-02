package com.musiclibrarysusie.routes

import com.musiclibrarysusie.dto.MusicService.*
import com.musiclibrarysusie.tables.Musics
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

private fun Route.musicRouting() {
    route("/GetMusic") {

        post {
            val musicRequest = call.receive<MusicRequest>()
            var response: MusicResponse? = null

            var mmm = ArrayList<Music>()

            transaction {
                Musics.selectAll().forEach{it-> mmm.add(
                    Music(
                        music_id = it[Musics.music_id],
                        music_name = it[Musics.music_name],
                        music_img = it[Musics.music_img],
                        music_source = it[Musics.music_source],
                        year = it[Musics.year]
                        )
                )}
//                    Musics.music_id eq musicRequest.id
//                }.limit(1).firstOrNull()?.let {
//                    response = MusicResponse(
//                        Music(
//                        music_id = it[Musics.music_id],
//                        music_name = it[Musics.music_name],
//                        music_img = it[Musics.music_img],
//                        music_source = it[Musics.music_source],
//                        year = it[Musics.year]
//                        )
//                    )
//
//                }
            }

            response?.let { it1 -> call.respond(mmm) }
        }

    }

    route("/GetMusicByAlbum") {
        post{
            val musicRequest = call.receive<GetMusicByAlbumRequest>()
            var response:GetMusicByAlbumResponse? = null

            transaction {  }

            response?.let{it->call.respond((it))}
        }
    }
}

fun Application.registerMusicRoutes() {
    routing {
        musicRouting()
    }
}