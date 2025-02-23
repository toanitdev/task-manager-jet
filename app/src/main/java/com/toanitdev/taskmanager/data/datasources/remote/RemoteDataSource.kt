package com.toanitdev.taskmanager.data.datasources.remote

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.UserProfileResponse

import okhttp3.ResponseBody
import retrofit2.Call

interface RemoteDataSource {

    suspend fun login(request: LoginRequest) : BaseResponse<LoginResponse>

    suspend fun getUserProfile(accessToken: String) : BaseResponse<UserProfileResponse>
}