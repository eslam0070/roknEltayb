package com.rokneltayb.data.dataSource.remote.home

import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.domain.entity.Result
import retrofit2.Response
import retrofit2.http.Query

interface HomeRemoteDataSource {

    suspend fun home(): Result<HomeResponse>
    suspend fun categories(): Result<CategoriesResponse>
    suspend fun products(categoryId:Int,sort:String,search:String): Result<ProductsResponse>
    suspend fun searchOnProducts(search:String): Result<ProductsResponse>
    suspend fun prorudtsWithOutSearch(): Result<ProductsResponse>
    suspend fun productDetails(productId:Int): Result<ProductDetailsResponse>


}