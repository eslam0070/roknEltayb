package com.rokneltayb.data.dataSource.remote.cart

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.DateResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.cart.delivery.DeliveryimesResponse
import com.rokneltayb.data.model.cart.update.UpdateCartResponse
import com.rokneltayb.domain.entity.Result
import retrofit2.Response
import retrofit2.http.GET

interface CartRemoteDataSource {

    suspend fun getCart(): Result<CartResponse>
    suspend fun addCart(productId: String, shapeId: String, count: String): Result<AddCartResponse>
    suspend fun updateCart(productId: String, shapeId: String, count: String): Result<UpdateCartResponse>
    suspend fun deleteCart(productId: String, shapeId: String): Result<DeleteCartResponse>
    suspend fun applyCouponCart(coupon: String): Result<AddCouponResponse>
    suspend fun deleteCouponCart(): Result<AddCouponResponse>
    suspend fun deliveryTimes(): Result<DeliveryimesResponse>
    suspend fun deliveryDates(): Result<DateResponse>
}