package com.rokneltayb.di

import android.app.Application
import android.content.Context
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.domain.entity.ErrorTypeHandler
import com.rokneltayb.domain.entity.ErrorTypeHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val baseUrl = "https://roknaltayeb.com/api/"

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideApiService(sharedPreferences: SharedPreferencesImpl): NetworkServices {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                chain.proceed(chain.request().newBuilder().also {
                    if (sharedPreferences.getApiKeyToken().isNotEmpty())
                        it.addHeader(
                            "Authorization", "Bearer " + sharedPreferences.getApiKeyToken()
                        )

                    if (sharedPreferences.getRefToken().isNotEmpty())
                        it.addHeader(
                            "lang", sharedPreferences.getLanguage())
                }.build())
            }.also { client ->

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
                client.connectTimeout(30, TimeUnit.SECONDS)
                client.readTimeout(30, TimeUnit.SECONDS)
                client.writeTimeout(30, TimeUnit.SECONDS)
            }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient).build()

        return retrofit.create(NetworkServices::class.java)
    }

    @Provides
    fun provideErrorTypeHandler(errorTypeHandlerImpl: ErrorTypeHandlerImpl): ErrorTypeHandler {
        return errorTypeHandlerImpl
    }


}