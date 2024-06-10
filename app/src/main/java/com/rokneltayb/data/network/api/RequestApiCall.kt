package com.rokneltayb.data.network.api



import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.entity.ErrorResponse
import retrofit2.Response
import javax.inject.Inject

class RequestApiCall @Inject constructor(
) {

    var gson: Gson = GsonBuilder().create()
    private val type = object : TypeToken<ErrorResponse>() {}.type

    suspend fun <T : Any> requestApiCall(requestApi: suspend () -> Response<T>): Result<T> {
        return try {
            val response = requestApi.invoke()
            parseApiResponse(response)
        }
        catch (exception: Exception) {
                val errorResponse: ErrorResponse? = gson.fromJson(requestApi.invoke().errorBody()!!.charStream(), type)
            Result.Error(errorResponse)
        }
    }

    private fun <T> parseApiResponse(response: Response<T>): Result<T> {
        try {
            if (response.isSuccessful) {
                response.body()?.let { apiRes ->
                    return Result.Success(apiRes)
                }
            }
            val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
            return Result.Error(message = errorResponse)
        }catch (e: Exception) {
            val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
            return Result.Error(errorResponse)
        }


    }
}