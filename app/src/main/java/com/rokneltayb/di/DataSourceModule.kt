package com.rokneltayb.di

import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSourceImpl
import com.rokneltayb.data.sharedPref.SharedPreferences
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    //data store local datasource
    @Binds
    abstract fun provideDataStore(sharedPreferencesImpl: SharedPreferencesImpl): SharedPreferences

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: UserRemoteDataSourceImpl): UserRemoteDataSource

}