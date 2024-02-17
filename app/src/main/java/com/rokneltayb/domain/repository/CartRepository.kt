package com.rokneltayb.domain.repository

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
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

interface CartRepository {
    suspend fun getCart(): Result<CartResponse>
    suspend fun addCart(productId: String, shapeId: String, count: String): Result<AddCartResponse>
    suspend fun updateCart(productId: String, shapeId: String, count: String): Result<UpdateCartResponse>

    suspend fun deleteCart(productId: String, shapeId: String): Result<DeleteCartResponse>

    suspend fun applyCouponCart(coupon: String): Result<AddCouponResponse>
    suspend fun deleteCouponCart(): Result<AddCouponResponse>
}