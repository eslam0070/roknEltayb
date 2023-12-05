package com.rokneltayb.data.network.api

import com.rokneltayb.data.sharedPref.SharedPreferences
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.Locale
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) : Interceptor {



    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        var request = chain.request()
        request = request
            .newBuilder()
            .header("Authorization", "Bearer${sharedPreferences.getApiKeyToken()}")
            .header("lang", sharedPreferences.getLanguage())
            .header("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }


}
