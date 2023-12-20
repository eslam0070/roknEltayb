package com.rokneltayb.data.repository


import com.rokneltayb.data.dataSource.remote.home.HomeRemoteDataSource
import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
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

class HomeRepositoryImpl @Inject constructor(private val remoteSource: HomeRemoteDataSource) :
    HomeRepository {
    override suspend fun home(): Result<HomeResponse> = remoteSource.home()
    override suspend fun categories(): Result<CategoriesResponse> = remoteSource.categories()

    override suspend fun products(
        categoryId: Int,
        sort: String,
        search: String
    ): Result<ProductsResponse> = remoteSource.products(categoryId, sort, search)

    override suspend fun prorudtsWithOutSearch(): Result<ProductsResponse> = remoteSource.prorudtsWithOutSearch()


    override suspend fun searchOnProducts(search: String): Result<ProductsResponse> = remoteSource.searchOnProducts(search)

    override suspend fun productDetails(productId: Int): Result<ProductDetailsResponse> =
        remoteSource.productDetails(productId)

}