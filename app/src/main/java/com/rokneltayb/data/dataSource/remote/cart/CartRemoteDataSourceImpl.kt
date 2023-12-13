package com.rokneltayb.data.dataSource.remote.cart

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import javax.inject.Inject
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result

class CartRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : CartRemoteDataSource {
    override suspend fun getCart(): Result<CartResponse> {
        val res = requestApiCall.requestApiCall { networkServices.getCart() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun addCart(
        productId: String,
        shapeId: String,
        count: String
    ): Result<AddCartResponse> {
        val res = requestApiCall.requestApiCall { networkServices.addCart(productId, shapeId, count) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun deleteCart(
        productId: String,
        shapeId: String
    ): Result<DeleteCartResponse> {
        val res = requestApiCall.requestApiCall { networkServices.deleteCart(productId, shapeId) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}