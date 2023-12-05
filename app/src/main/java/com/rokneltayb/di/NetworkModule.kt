package com.rokneltayb.di

import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.AuthInterceptor
import com.rokneltayb.data.sharedPref.SharedPreferences
import com.rokneltayb.domain.entity.ErrorTypeHandler
import com.rokneltayb.domain.entity.ErrorTypeHandlerImpl
import com.squareup.moshi.Moshi
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
    fun provideApiService(): NetworkServices {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkServices::class.java)
    }

    @Provides
    fun provideErrorTypeHandler(errorTypeHandlerImpl: ErrorTypeHandlerImpl): ErrorTypeHandler {
        return errorTypeHandlerImpl
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
    @Provides
    fun provideAuthInterceptor(sharedPreferences: SharedPreferences): AuthInterceptor {
        return AuthInterceptor(sharedPreferences)
    }

}