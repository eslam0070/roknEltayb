package com.rokneltayb.domain.usecase

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
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.UserRepository
import okhttp3.RequestBody
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun home(): Result<HomeResponse> = repo.home()

    suspend fun categories(): Result<CategoriesResponse> = repo.categories()
    suspend fun products(categoryId:String,sort:String,search:String,type:String): Result<ProductsResponse> = repo.products(categoryId, sort, search,type)
    suspend fun prorudtsWithOutSearch(): Result<ProductsResponse> = repo.prorudtsWithOutSearch()
    suspend fun searchOnProducts(search:String): Result<ProductsResponse> = repo.searchOnProducts(search)
    suspend fun productDetails(productId:Int): Result<ProductDetailsResponse> = repo.productDetails(productId)
    suspend fun getCart2(): Result<CartResponse> = repo.getCart2()
    suspend fun applyCouponCart2(coupon: RequestBody): Result<AddCouponResponse> = repo.applyCouponCart2(coupon)
}