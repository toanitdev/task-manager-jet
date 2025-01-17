package com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("nick_name")
    val nickName: String
)
