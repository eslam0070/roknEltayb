package com.rokneltayb.data.repository


import com.rokneltayb.data.dataSource.remote.cart.CartRemoteDataSource
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.cart.delivery.DeliveryimesResponse
import com.rokneltayb.data.model.cart.update.UpdateCartResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val remoteSource: CartRemoteDataSource): CartRepository {
    override suspend fun getCart(): Result<CartResponse> = remoteSource.getCart()
    override suspend fun addCart(productId: String, shapeId: String, count: String): Result<AddCartResponse> = remoteSource.addCart(productId, shapeId, count)
    override suspend fun updateCart(productId: String, shapeId: String, count: String): Result<UpdateCartResponse> = remoteSource.updateCart(productId, shapeId, count)
    override suspend fun deleteCart(productId: String, shapeId: String): Result<DeleteCartResponse> = remoteSource.deleteCart(productId, shapeId)

    override suspend fun applyCouponCart(coupon: String): Result<AddCouponResponse> = remoteSource.applyCouponCart(coupon)
    override suspend fun deleteCouponCart(): Result<AddCouponResponse> = remoteSource.deleteCouponCart()
    override suspend fun deliveryTimes(): Result<DeliveryimesResponse> = remoteSource.deliveryTimes()

}