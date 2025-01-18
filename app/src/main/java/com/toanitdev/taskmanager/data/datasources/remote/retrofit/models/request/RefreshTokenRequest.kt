package com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refresh_token")
    val refreshToken: String
)