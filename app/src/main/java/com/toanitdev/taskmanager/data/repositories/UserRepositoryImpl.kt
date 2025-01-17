package com.toanitdev.taskmanager.data.repositories

import com.toanitdev.taskmanager.data.datasources.remote.RemoteDataSource
import com.toanitdev.taskmanager.data.datasources.remote.middleware.ApiResult
import com.toanitdev.taskmanager.data.datasources.remote.middleware.handleApiCall
import com.toanitdev.taskmanager.data.datasources.shared.SharedDataSource
import com.toanitdev.taskmanager.domain.entities.User
import com.toanitdev.taskmanager.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val sharedDataSource: SharedDataSource,
) : UserRepository {
    override fun getUserProfile(): Flow<ApiResult<User>> {
        return flow {

            val result = handleApiCall { remoteDataSource.getUserProfile("Bearer " + sharedDataSource.loadAccessToken()) }
            when(result) {
                is ApiResult.Success -> {
                    result.data.data?.let {
                        emit(ApiResult.Success(
                            User(
                                uid = it.userId,
                                username = it.username,
                                nickName = it.nickName
                            )
                        ))
                    }
                }

                is ApiResult.Failure -> {
                    emit(result)
                }
            }
        }
    }
}