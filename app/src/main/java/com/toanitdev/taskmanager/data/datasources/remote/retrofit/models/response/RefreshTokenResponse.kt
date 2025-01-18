package com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("access_token")
    val accessToken: String
)