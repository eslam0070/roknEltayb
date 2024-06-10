package com.rokneltayb.data.dataSource.remote.home

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
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
import okhttp3.RequestBody
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
        page:Int,
        categoryId: String,
        sort: String,
        search: String,
        type:String
    ): Result<ProductsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.products(page,categoryId, sort, search,type) }

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

    override suspend fun getCart2(): Result<CartResponse> {
        val res = requestApiCall.requestApiCall { networkServices.getCart2() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun applyCouponCart2(coupon: RequestBody): Result<AddCouponResponse> {
        val res = requestApiCall.requestApiCall { networkServices.applyCouponCart2(coupon) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}