package com.musiclibrarysusie.routes

import com.musiclibrarysusie.dto.AccountService.AccountRequest
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*

fun Route.authRouting() {
    route("/auth") {

        post {
            val musicRequest = call.receive<AccountRequest>()
//            var response: MusicResponse? = null
//
//            transaction {
//                Music.select {
//                    Music.music_id eq musicRequest.id
//                }.limit(1).firstOrNull()?.let {
//                    response = MusicResponse(
//                        music_id = it[Music.music_id],
//                        music_name = it[Music.music_name],
//                        music_img = it[Music.music_img],
//                        music_source = it[Music.music_source],
//                        year = it[Music.year]
//                    )
//                }
//            }
//
//            response?.let { it1 -> call.respond(it1) }
        }

    }
}

fun Application.registerAuthRoutes() {
    routing {
        authRouting()
    }
}