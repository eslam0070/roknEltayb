package com.rokneltayb.data.repository


import com.rokneltayb.data.dataSource.remote.cart.CartRemoteDataSource
import com.rokneltayb.data.dataSource.remote.home.HomeRemoteDataSource
import com.rokneltayb.data.dataSource.remote.rate.RateRemoteDataSource
import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.rate.AddRateResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.RateRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(private val remoteSource: RateRemoteDataSource): RateRepository {
    override suspend fun storeRate(userId:Int,productId:Int,rate:String,description:String): Result<AddRateResponse> = remoteSource.storeRate(userId, productId, rate, description)

}