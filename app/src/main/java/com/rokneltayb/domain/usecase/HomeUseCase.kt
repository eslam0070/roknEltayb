package com.rokneltayb.domain.usecase

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
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repo: HomeRepository) {
    suspend fun home(): Result<HomeResponse> = repo.home()

    suspend fun categories(): Result<CategoriesResponse> = repo.categories()
    suspend fun products(categoryId:Int,sort:String,search:String): Result<ProductsResponse> = repo.products(categoryId, sort, search)
    suspend fun prorudtsWithOutSearch(): Result<ProductsResponse> = repo.prorudtsWithOutSearch()
    suspend fun searchOnProducts(search:String): Result<ProductsResponse> = repo.searchOnProducts(search)
    suspend fun productDetails(productId:Int): Result<ProductDetailsResponse> = repo.productDetails(productId)

}