package com.musiclibrarysusie

import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

//@Suppress("unused") // Referenced in application.conf
//@kotlin.jvm.JvmOverloads
//fun Application.module(testing: Boolean = false) {
//}
//
//object MusicalGenres: Table() {
//    val genre_id: Column<Int> = integer("genre_id").autoIncrement()
//    val genre_banner: Column<String> = varchar("genre_banner", 100)
//    val genre_name: Column<String> = varchar("genre_name", 100)
//    override val primaryKey = PrimaryKey(genre_id)
//}
//
//fun main() {
////    Database.connect("jdbc:postgresql://localhost:5432/MusicDb", driver = "org.postgresql.Driver", user = "postgres", password = "29091998")//postgres://postgres:29091998@YourHostname:5432/YourDatabaseName
////    transaction {
////        SchemaUtils.create(MusicalGenres)
////
////        MusicalGenres.insert {
////            it[genre_name] = "Rap"
////            it[genre_banner] = "static/musicgenre/rap.png"
////        }
////    }
//
//    val port = System.getenv("PORT")?.toInt() ?: 23567
//
//    embeddedServer(Netty, port = port) {
//
//        routing {
//            get("/") {
//                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//            }
////
////            // Static feature. Try to access `/static/ktor_logo.svg`
////            static("/static") {
////                resources("static")
////
////                static("musicgenre") {
////
////                }
////                static("musicartist") {
////
////                }
////            }
////
////            get("/json/gson") {
////                call.respond(mapOf("hello" to "world"))
////            }
//        }
//
//    }.start(wait = true)
//}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
    }
}

