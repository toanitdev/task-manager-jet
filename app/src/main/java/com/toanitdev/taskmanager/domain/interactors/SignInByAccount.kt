package com.toanitdev.taskmanager.domain.interactors

import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.domain.entities.LoginToken
import com.toanitdev.taskmanager.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInByAccount @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<SignInByAccount.Param, Flow<ApiResult<LoginToken>>>() {


    override fun execute(param: Param): Flow<ApiResult<LoginToken>> {
        return authRepository.login(LoginRequest(param.username, param.password))
    }

    data class Param(
        val username: String,
        val password: String
    )

}