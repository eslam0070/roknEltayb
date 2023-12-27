package com.rokneltayb.di

import com.rokneltayb.data.dataSource.remote.rate.RateRemoteDataSource
import com.rokneltayb.data.dataSource.remote.rate.RateRemoteDataSourceImpl
import com.rokneltayb.data.repository.AddressRepositoryImpl
import com.rokneltayb.data.repository.CartRepositoryImpl
import com.rokneltayb.data.repository.FavoritesRepositoryImpl
import com.rokneltayb.data.repository.HomeRepositoryImpl
import com.rokneltayb.data.repository.RateRepositoryImpl
import com.rokneltayb.data.repository.UserRepositoryImpl
import com.rokneltayb.domain.repository.AddressRepository
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.FavoritesRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.RateRepository
import com.rokneltayb.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
    @Binds
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
    @Binds
    abstract fun provideCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
    @Binds
    abstract fun provideRateRepository(rateRepositoryImpl: RateRepositoryImpl): RateRepository
    @Binds
    abstract fun provideFavortiesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository
    @Binds
    abstract fun provideAddressRepository(addressRepositoryImpl: AddressRepositoryImpl): AddressRepository
}