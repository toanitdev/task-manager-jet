package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.domain.entities.User
import com.toanitdev.taskmanager.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfile @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<GetUserProfile.Param, Flow<ApiResult<User>>>() {


    class Param {}

    override fun execute(param: Param): Flow<ApiResult<User>> {
        return userRepository.getUserProfile()
    }
}