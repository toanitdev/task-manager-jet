package com.toanitdev.taskmanager.data.datasources.remote.middleware

import retrofit2.HttpException
import java.io.IOException


suspend fun <T> handleApiCall(
    call: suspend () -> T,
): ApiResult<T> {
    try {
        return ApiResult.Success(call())
    } catch (e: IOException) {
        println("Network error: ${e.message}")
        return ApiResult.Failure(CallFailure.IOFailure(e.message ?: ""))
    } catch (e: HttpException) {
        println("HTTP error: ${e.code()}")
        return ApiResult.Failure(CallFailure.HttpFailure(e.code(), e.message ?: ""))
    } catch (e: Exception) {
        println("Unknown error: ${e.message}")
        return ApiResult.Failure(CallFailure.UnknownFailure(e.message ?: ""))
    }
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failure(val failure: CallFailure) :
        ApiResult<Nothing>()
}

sealed class CallFailure {

    data class IOFailure(val message: String) : CallFailure()
    data class HttpFailure(val code: Int, val message: String) : CallFailure()
    data class UnknownFailure(val message: String) : CallFailure()
}