package com.rokneltayb.data.network.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.ErrorTypeHandler
import com.rokneltayb.domain.entity.ErrorType
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.entity.Result2

import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class RequestApiCall2 @Inject constructor(
) {

    var gson: Gson = GsonBuilder().create()
    private val type = object : TypeToken<ErrorResponse>() {}.type

    suspend fun <T : Any> requestApiCall(requestApi: suspend () -> Response<T>): Result2<T> {
        return try {
            val response = requestApi.invoke()
            parseApiResponse(response)
        } catch (exception: Exception) {
            val errorResponse: ErrorResponse? = gson.fromJson(requestApi.invoke().errorBody()!!.charStream(), type)
            Result2.Error(errorResponse)
        }
    }

    private fun <T> parseApiResponse(response: Response<T>): Result2<T> {
        try {
            if (response.isSuccessful) {
                response.body()?.let { apiRes ->
                    return Result2.Success(apiRes)
                }
            }
            val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
            return Result2.Error(message = errorResponse)
        }catch (e: Exception) {
            val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
            return Result2.Error(errorResponse)
        }


    }
}