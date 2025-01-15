package com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response
data class BaseResponse<Data>(
    val http_code: Int,
    val message: String,
    val data: Data?,
)
