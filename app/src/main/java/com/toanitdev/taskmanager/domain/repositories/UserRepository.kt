package com.toanitdev.taskmanager.domain.repositories

import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserProfile() : Flow<ApiResult<User>>
}

