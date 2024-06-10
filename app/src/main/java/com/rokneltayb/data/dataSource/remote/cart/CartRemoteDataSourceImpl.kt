package com.rokneltayb.data.dataSource.remote.cart

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.cart.delivery.DeliveryimesResponse
import com.rokneltayb.data.model.cart.update.UpdateCartResponse
import javax.inject.Inject
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result
import retrofit2.Response

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

    override suspend fun updateCart(
        productId: String,
        shapeId: String,
        count: String
    ): Result<UpdateCartResponse> {
        val res = requestApiCall.requestApiCall { networkServices.updateCart(productId, shapeId, count) }

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

    override suspend fun applyCouponCart(coupon: String): Result<AddCouponResponse> {
        val res = requestApiCall.requestApiCall { networkServices.applyCouponCart(coupon) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun deleteCouponCart(): Result<AddCouponResponse> {
        val res = requestApiCall.requestApiCall { networkServices.deleteCouponCart() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun deliveryTimes(): Result<DeliveryimesResponse> {
        val res = requestApiCall.requestApiCall { networkServices.deliveryTimes() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}