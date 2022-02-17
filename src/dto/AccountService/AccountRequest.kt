package com.musiclibrarysusie.dto.AccountService

import com.musiclibrarysusie.tables.Accounts

data class AccountRequest(
    val username: String,
    val password: String,
    val email: String
)