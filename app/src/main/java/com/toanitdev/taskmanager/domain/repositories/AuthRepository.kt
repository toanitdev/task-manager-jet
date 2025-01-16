package com.toanitdev.taskmanager.domain.repositories

import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.domain.entities.LoginToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {


    fun login(request: LoginRequest) : Flow<ApiResult<LoginToken>>
}