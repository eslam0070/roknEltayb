package com.rokneltayb.data.dataSource.remote.orders

import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.orders.OrdersResponse
import com.rokneltayb.data.model.orders.add.AddOrderResponse
import com.rokneltayb.data.model.orders.details.OrderDetailsResponse
import com.rokneltayb.domain.entity.Result

interface OrdersRemoteDataSource {
    suspend fun getOrders(): Result<OrdersResponse>
    suspend fun getOrderDetails(orderId:Int): Result<OrderDetailsResponse>
    suspend fun addOrder(addressId:Int,deliveryTimeId:Int):Result<AddOrderResponse>

}