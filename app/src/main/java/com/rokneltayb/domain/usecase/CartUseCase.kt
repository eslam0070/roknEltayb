package com.rokneltayb.domain.usecase

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
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(private val repo: CartRepository) {
    suspend fun getCart(): Result<CartResponse> = repo.getCart()
    suspend fun addCart(productId: String, shapeId: String, count: String): Result<AddCartResponse> = repo.addCart(productId, shapeId, count)
    suspend fun deleteCart(productId: String, shapeId: String): Result<DeleteCartResponse> = repo.deleteCart(productId, shapeId)
}