package com.rokneltayb.data.dataSource.remote.home

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.domain.entity.Result
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Query

interface HomeRemoteDataSource {

    suspend fun home(): Result<HomeResponse>
    suspend fun categories(): Result<CategoriesResponse>
    suspend fun products(categoryId:String,sort:String,search:String,type:String): Result<ProductsResponse>
    suspend fun searchOnProducts(search:String): Result<ProductsResponse>
    suspend fun prorudtsWithOutSearch(): Result<ProductsResponse>
    suspend fun productDetails(productId:Int): Result<ProductDetailsResponse>
    suspend fun getCart2(): Result<CartResponse>
    suspend fun applyCouponCart2(coupon: RequestBody): Result<AddCouponResponse>



}