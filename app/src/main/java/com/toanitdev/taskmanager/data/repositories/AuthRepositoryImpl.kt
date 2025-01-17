package com.toanitdev.taskmanager.data.repositories

import com.toanitdev.taskmanager.data.datasources.local.room.RoomDataSource
import com.toanitdev.taskmanager.data.datasources.remote.RemoteDataSource
import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.data.datasources.remote.middleware.handleApiCall
import com.toanitdev.taskmanager.data.datasources.remote.retrofit.models.request.LoginRequest
import com.toanitdev.taskmanager.data.datasources.shared.SharedDataSource
import com.toanitdev.taskmanager.domain.entities.LoginToken
import com.toanitdev.taskmanager.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val sharedDataSource: SharedDataSource,
) : AuthRepository {
    override fun login(request: LoginRequest): Flow<ApiResult<LoginToken>> {
        return flow {
            val result = handleApiCall(call = { remoteDataSource.login(request) })
            when (result) {
                is ApiResult.Failure -> emit(result)
                is ApiResult.Success -> {
                    result.data.data?.let {
                        emit(
                            ApiResult.Success(
                                LoginToken(
                                    userId = it.userId,
                                    username = it.username,
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken,
                                    tokenType = it.tokenType,
                                    expiresIn = it.expiresIn,
                                    refreshExpiresIn = it.refreshExpiresIn
                                )
                            )
                        )

                        sharedDataSource.saveAccessToken(it.accessToken)
                        sharedDataSource.saveFreshToken(it.refreshToken)
                    }



                }
            }
        }
    }

}

