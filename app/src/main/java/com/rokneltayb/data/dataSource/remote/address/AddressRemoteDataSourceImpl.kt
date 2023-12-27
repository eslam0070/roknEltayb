package com.rokneltayb.data.dataSource.remote.address

import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import javax.inject.Inject
import com.rokneltayb.data.network.NetworkServices
import com.rokneltayb.data.network.api.RequestApiCall
import com.rokneltayb.domain.entity.Result

class AddressRemoteDataSourceImpl @Inject constructor(
    private val networkServices: NetworkServices,
    private val requestApiCall: RequestApiCall
) : AddressRemoteDataSource {


    override suspend fun address(): Result<AddressResponse> {
        val res = requestApiCall.requestApiCall { networkServices.address() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun cities(): Result<CityResponse> {
        val res = requestApiCall.requestApiCall { networkServices.cities() }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun addAddress(
        name: String,
        phone: String,
        cityId: String,
        block: String,
        street: String,
        avenue: String,
        buildingNum: String,
        floorNum: String,
        apartment: String,
        address: String
    ): Result<AddAddressResponse> {
        val res = requestApiCall.requestApiCall { networkServices.addAddress(name, phone, cityId, block, street, avenue, buildingNum, floorNum, apartment, address) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }

    override suspend fun deleteAddress(id: Int): Result<DeleteAddressResponse> {
        val res = requestApiCall.requestApiCall { networkServices.deleteAddress(id) }

        return if (res is Result.Success && res.data != null)
            Result.Success(res.data)
        else
            Result.Error(res.errorType)
    }


}