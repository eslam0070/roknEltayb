package com.rokneltayb.data.dataSource.remote.address

import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.address.details.AddressDetailsResponse
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import com.rokneltayb.domain.entity.Result
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Query

interface AddressRemoteDataSource {

    suspend fun address(): Result<AddressResponse>
    suspend fun getAddressById(id:String): Result<AddressDetailsResponse>

    suspend fun cities(): Result<CityResponse>
    suspend fun addAddress(name:String,
                           phone:String,
                           cityId:String,
                           block:String,
                           street:String,
                           avenue:String,
                           buildingNum:String,
                           floorNum:String,
                           apartment:String,
                           address:String):Result<AddAddressResponse>

    suspend fun updateAddress(id:String,name:String,
                           phone:String,
                           cityId:String,
                           block:String,
                           street:String,
                           avenue:String,
                           buildingNum:String,
                           floorNum:String,
                           apartment:String,
                           address:String):Result<AddressDetailsResponse>

    suspend fun deleteAddress(id:Int):Result<DeleteAddressResponse>

}