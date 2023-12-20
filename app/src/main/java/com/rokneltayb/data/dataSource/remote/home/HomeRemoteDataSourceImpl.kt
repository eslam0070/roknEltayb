package com.rokneltayb.data.dataSource.remote.home

import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import javax.inject.Inject
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result
import retrofit2.Response

class HomeRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : HomeRemoteDataSource {
    override suspend fun home(): Result<HomeResponse> {
        val res = requestApiCall.requestApiCall { networkServices.home() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun categories(): Result<CategoriesResponse> {
        val res = requestApiCall.requestApiCall { networkServices.categories() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun products(
        categoryId: Int,
        sort: String,
        search: String
    ): Result<ProductsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.products(categoryId, sort, search) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun searchOnProducts(search: String): Result<ProductsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.searchOnProducts(search) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun prorudtsWithOutSearch(): Result<ProductsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.prorudtsWithOutSearch() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun productDetails(productId: Int): Result<ProductDetailsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.productDetails(productId) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}