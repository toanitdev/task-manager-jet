package com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response



data class LoginResponse(
    val user_id: Int,
    val username: String,
    val access_token: String,
    val refresh_token: String,
    val token_type: String,
    val expires_in: String,
    val refresh_expires_in: String,
)


