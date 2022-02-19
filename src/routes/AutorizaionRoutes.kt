package com.musiclibrarysusie.routes

import com.musiclibrarysusie.dto.AccountService.AccountRequest
import com.musiclibrarysusie.dto.AccountService.AccountResponse
import com.musiclibrarysusie.tables.Accounts
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

private fun Route.authRouting() {
    route("/auth") {

        post {
            val authRequest = call.receive<AccountRequest>()
            var response = AccountResponse("err")

            transaction {
                Accounts.select {
                    (Accounts.email eq authRequest.email) and (Accounts.password eq authRequest.password)
                }.limit(1).firstOrNull()?.let {
                    response = AccountResponse("Confirm")
                }
            }

            //response.let { it1 ->
                call.respond(response)
        //}
        }

    }
}

fun Application.registerAuthRoutes() {
    routing {
        authRouting()
    }
}