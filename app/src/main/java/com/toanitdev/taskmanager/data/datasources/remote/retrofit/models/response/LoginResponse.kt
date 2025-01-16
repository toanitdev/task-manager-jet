package com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: String,
    @SerializedName("refresh_expires_in")
    val refreshExpiresIn: String
)




