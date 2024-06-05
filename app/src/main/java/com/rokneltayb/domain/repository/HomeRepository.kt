package com.rokneltayb.domain.repository

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import okhttp3.RequestBody

interface HomeRepository {
    suspend fun home(): Result<HomeResponse>
    suspend fun categories(): Result<CategoriesResponse>
    suspend fun products(categoryId:String,sort:String,search:String,type:String): Result<ProductsResponse>
    suspend fun prorudtsWithOutSearch(): Result<ProductsResponse>
    suspend fun searchOnProducts(search:String): Result<ProductsResponse>
    suspend fun productDetails(productId:Int): Result<ProductDetailsResponse>

    suspend fun getCart2(): Result<CartResponse>
    suspend fun applyCouponCart2(coupon: RequestBody): Result<AddCouponResponse>
}