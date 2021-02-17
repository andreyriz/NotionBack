package com.musiclibrarysusie

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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

object Music : Table() {
    val music_id: Column<Int> = integer("music_id").autoIncrement()
    val music_name: Column<String> = varchar("music_name", 100)
    val music_img: Column<String> = varchar("music_img", 100)
    val music_source: Column<String> = varchar("music_source", 100)
    override val primaryKey = PrimaryKey(music_id)
}

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

//Host ec2-54-72-155-238.eu-west-1.compute.amazonaws.com
//Database d9bmg57phrms4f
//User gwaegjctdegngg
//Port 5432
//Password fec810cbc07387ab2b4e1a5080ae3dab0d033d56abddf7f38220bae69321465f
//URI postgres://gwaegjctdegngg:fec810cbc07387ab2b4e1a5080ae3dab0d033d56abddf7f38220bae69321465f@ec2-54-72-155-238.eu-west-1.compute.amazonaws.com:5432/d9bmg57phrms4f

//dataSourceClassName=org.postgresql.ds.PGSimpleDataSource
//dataSource.user=<username>
//dataSource.password=<password>
//dataSource.databaseName=<dbName>
//dataSource.portNumber=<serverPort>
//dataSource.serverName=<serverName>

private fun hikari(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.postgresql.Driver"
        //System.getenv("JDBC_DRIVER") // 1
    config.jdbcUrl = System.getenv("DATABASE_URL") // 2
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    val user = System.getenv("DATABASE_USER") // 3
    if (user != null) {
        config.username = user
    }
    val password = System.getenv("DATABASE_PASS") // 4
    if (password != null) {
        config.password = password
    }
    config.validate()
    return HikariDataSource(config)
}

// 5
suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val port = System.getenv("PORT")?.toInt() ?: 23567

    Database.connect(
        hikari()
        //System.getenv("DATABASE_URL"),
        //"jdbc:postgresql://localhost:5432/MusicDb?user=postgres&password=29091998",
        //driver = "org.postgresql.Driver"
    )
    //postgres://postgres:29091998@YourHostname:5432/YourDatabaseName , user = "postgres", password = "29091998"

    transaction {
        SchemaUtils.create(Music)

        Music.insert {
            it[music_name]="Roket"
            it[music_img]="https://musiclibrary-susie.herokuapp.com/static/musicartist/roket_main_img.jpg"
            it[music_source]="https://musiclibrary-susie.herokuapp.com/static/musicsource/rocket-monday.mp3"
        }
    }

    embeddedServer(Netty, port = port) {
        routing {

            static("/static") {
                resources("static")

                static("musicgenre") {

                }
                static("musicartist") {

                }
                static("musicsource") {

                }
            }

            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }

            get("/getArtist") {
                var users=""
                transaction {
                    Music.selectAll().forEach{users+="${it[Music.music_name]} ${it[Music.music_source]} ${it[Music.music_img]}"}
                }
                call.respondText(users, contentType = ContentType.Text.Plain)
            }
        }
    }.start(wait = true)
}