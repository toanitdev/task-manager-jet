package com.toanitdev.taskmanager.domain.entities

data class LoginToken(
    val userId: Int,
    val username: String,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: String,
    val refreshExpiresIn: String
)