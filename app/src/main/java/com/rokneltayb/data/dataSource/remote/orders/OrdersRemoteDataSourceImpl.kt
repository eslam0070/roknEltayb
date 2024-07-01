package com.rokneltayb.data.dataSource.remote.orders

import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.orders.OrdersResponse
import com.rokneltayb.data.model.orders.add.AddOrderResponse
import com.rokneltayb.data.model.orders.details.OrderDetailsResponse
import javax.inject.Inject
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result

class OrdersRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : OrdersRemoteDataSource {
    override suspend fun getOrders(): Result<OrdersResponse> {
        val res = requestApiCall.requestApiCall { networkServices.getOrders() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun getOrderDetails(orderId: Int): Result<OrderDetailsResponse> {
        val res = requestApiCall.requestApiCall { networkServices.getOrderDetails(orderId) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun addOrder(addressId: Int,delivery_date:String,deliveryTimeId:Int): Result<AddOrderResponse> {
        val res = requestApiCall.requestApiCall { networkServices.addOrder(addressId,delivery_date,deliveryTimeId) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}