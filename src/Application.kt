package com.musiclibrarysusie

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
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
import java.net.URI

object Music : Table() {
    val music_id: Column<Int> = integer("music_id").autoIncrement()
    val music_name: Column<String> = varchar("music_name", 100)
    val music_img: Column<String> = varchar("music_img", 100)
    val music_source: Column<String> = varchar("music_source", 100)
    val year:Column<Int> = integer("year_of_issue")
    override val primaryKey = PrimaryKey(music_id)
}

data class SerializableMusic(
    val music_name: String,
    val music_img: String,
    val music_source: String
)

private fun hikari(): HikariDataSource {
    val config = HikariConfig()

    config.driverClassName = System.getenv("JDBC_DRIVER")
    var dbUri = URI(System.getenv("DATABASE_URL"))
    val username = dbUri.userInfo.split(":").toTypedArray()[0]
    val password = dbUri.userInfo.split(":").toTypedArray()[1]
    val dbUrl =
        "jdbc:postgresql://" + dbUri.host + ':' + dbUri.port + dbUri.path + "?sslmode=require" + "&user=$username&password=$password"
    config.jdbcUrl = dbUrl
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    return HikariDataSource(config)
}

suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction { block() }
    }

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val port = System.getenv("PORT")?.toInt() ?: 23567
    Database.connect(
        hikari()
    )

    embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {

            static("/static") {
                resources("static")

                static("musicgenre") {

                }
                static("musicartist") {

                }
                static("musicsource") {

                }
                static("musicimagesource") {

                }
            }

            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }

            get("/getArtist") {
                var music = ArrayList<SerializableMusic>()
                transaction {
                    Music.selectAll().forEach {
                        music.add(
                            SerializableMusic(
                                music_name = it[Music.music_name],
                                music_img = it[Music.music_img],
                                music_source = it[Music.music_source]
                            )
                        )
                    }
                }
                call.respond(music)
            }
        }
    }.start(wait = true)
}