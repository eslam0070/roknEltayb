package com.rokneltayb.data.dataSource.remote.cart

import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.domain.entity.Result

interface CartRemoteDataSource {

    suspend fun getCart(): Result<CartResponse>
    suspend fun addCart(productId: String, shapeId: String, count: String): Result<AddCartResponse>
    suspend fun deleteCart(productId: String, shapeId: String): Result<DeleteCartResponse>
}