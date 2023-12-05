package com.exas.crm.data.network.api

import android.util.Log
import com.exas.crm.domain.entity.ErrorType
import com.exas.crm.domain.entity.ErrorTypeHandler
import retrofit2.Response
import com.exas.crm.domain.entity.Result
import javax.inject.Inject

class RequestApiCall @Inject constructor(
    private val errorTypeHandler: ErrorTypeHandler
) {
    suspend fun <T : Any> requestApiCall(requestApi: suspend () -> Response<T>): Result<T> {
        return try {
            val response = requestApi.invoke()
            parseApiResponse(response)
        } catch (exception: Exception) {
            Result.Error(errorTypeHandler.getError(exception))
        }
    }

    private fun <T> parseApiResponse(response: Response<T>): Result<T> {
        try {
            if (response.isSuccessful) {
                response.body()?.let { apiRes ->
                    return Result.Success(apiRes)
                }
            }
            return Result.Error(ErrorType.ServerError)
        } catch (e: Exception) {
            return Result.Error(errorTypeHandler.getError(e))
        }
    }
}