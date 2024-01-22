package com.rokneltayb.di

import com.rokneltayb.data.dataSource.remote.address.AddressRemoteDataSource
import com.rokneltayb.data.dataSource.remote.address.AddressRemoteDataSourceImpl
import com.rokneltayb.data.dataSource.remote.cart.CartRemoteDataSource
import com.rokneltayb.data.dataSource.remote.cart.CartRemoteDataSourceImpl
import com.rokneltayb.data.dataSource.remote.favorite.FavoritesRemoteDataSource
import com.rokneltayb.data.dataSource.remote.favorite.FavoritesRemoteDataSourceImpl
import com.rokneltayb.data.dataSource.remote.home.HomeRemoteDataSource
import com.rokneltayb.data.dataSource.remote.home.HomeRemoteDataSourceImpl
import com.rokneltayb.data.dataSource.remote.orders.OrdersRemoteDataSource
import com.rokneltayb.data.dataSource.remote.orders.OrdersRemoteDataSourceImpl
import com.rokneltayb.data.dataSource.remote.rate.RateRemoteDataSource
import com.rokneltayb.data.dataSource.remote.rate.RateRemoteDataSourceImpl
import com.rokneltayb.data.dataSource.remote.settings.SettingsRemoteDataSource
import com.rokneltayb.data.dataSource.remote.settings.SettingsRemoteDataSourceImpl
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

    @Binds
    abstract fun provideHomeRemoteDataSource(remoteDataSource: HomeRemoteDataSourceImpl): HomeRemoteDataSource

    @Binds
    abstract fun provideCartRemoteDataSource(remoteDataSource: CartRemoteDataSourceImpl): CartRemoteDataSource

    @Binds
    abstract fun provideRateRemoteDataSource(remoteDataSource: RateRemoteDataSourceImpl): RateRemoteDataSource
    @Binds
    abstract fun provideFavoritesRemoteDataSource(remoteDataSource: FavoritesRemoteDataSourceImpl): FavoritesRemoteDataSource
    @Binds
    abstract fun provideAddressRemoteDataSource(remoteDataSource: AddressRemoteDataSourceImpl): AddressRemoteDataSource
    @Binds
    abstract fun provideSettingsRemoteDataSource(remoteDataSource: SettingsRemoteDataSourceImpl): SettingsRemoteDataSource
    @Binds
    abstract fun provideOrdersRemoteDataSource(remoteDataSource: OrdersRemoteDataSourceImpl): OrdersRemoteDataSource

}