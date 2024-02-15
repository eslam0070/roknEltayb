package com.rokneltayb.data.repository


import com.rokneltayb.data.dataSource.remote.cart.CartRemoteDataSource
import com.rokneltayb.data.dataSource.remote.home.HomeRemoteDataSource
import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.cart.update.UpdateCartResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val remoteSource: CartRemoteDataSource): CartRepository {
    override suspend fun getCart(): Result<CartResponse> = remoteSource.getCart()
    override suspend fun addCart(productId: String, shapeId: String, count: String): Result<AddCartResponse> = remoteSource.addCart(productId, shapeId, count)
    override suspend fun updateCart(productId: String, shapeId: String, count: String): Result<UpdateCartResponse> = remoteSource.updateCart(productId, shapeId, count)
    override suspend fun deleteCart(productId: String, shapeId: String): Result<DeleteCartResponse> = remoteSource.deleteCart(productId, shapeId)

}