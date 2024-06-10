package com.rokneltayb.data.dataSource.remote.rate

import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import javax.inject.Inject
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.data.model.rate.AddRateResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result
import retrofit2.http.Field

class RateRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : RateRemoteDataSource {
    override suspend fun storeRate(userId:Int,productId:Int,rate:String,description:String): Result<AddRateResponse> {
        val res = requestApiCall.requestApiCall { networkServices.storeRate(userId, productId, rate, description) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }
}