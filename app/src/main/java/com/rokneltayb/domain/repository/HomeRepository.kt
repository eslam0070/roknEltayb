package com.rokneltayb.domain.repository

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

interface HomeRepository {
    suspend fun home(): Result<HomeResponse>
    suspend fun categories(): Result<CategoriesResponse>
    suspend fun products(categoryId:Int,sort:String,search:String): Result<ProductsResponse>
    suspend fun productDetails(productId:Int): Result<ProductDetailsResponse>
}