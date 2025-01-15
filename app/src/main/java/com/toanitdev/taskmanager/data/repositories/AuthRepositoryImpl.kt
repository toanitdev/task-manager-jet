package com.toanitdev.taskmanager.data.repositories

import com.toanitdev.taskmanager.data.datasources.local.room.RoomDataSource
import com.toanitdev.taskmanager.data.datasources.remote.RemoteDataSource
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.BaseResponse
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.response.LoginResponse
import com.toanitdev.taskmanager.domain.repositories.AuthRepository
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource,
    private val remoteDatasource: RemoteDataSource,
) : AuthRepository{
    override fun login(request: LoginRequest): Call<BaseResponse<LoginResponse>> {
        return remoteDatasource.login(request)
    }
}