package com.toanitdev.taskmanager.data.datasources.remote

import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse

import okhttp3.ResponseBody
import retrofit2.Call

interface RemoteDataSource {

    fun login(request: LoginRequest) : Call<BaseResponse<LoginResponse>>
}