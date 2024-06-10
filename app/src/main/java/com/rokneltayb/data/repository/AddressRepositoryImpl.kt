package com.rokneltayb.data.repository


import com.rokneltayb.data.dataSource.remote.address.AddressRemoteDataSource
import com.rokneltayb.data.dataSource.remote.cart.CartRemoteDataSource
import com.rokneltayb.data.dataSource.remote.favorite.FavoritesRemoteDataSource
import com.rokneltayb.data.dataSource.remote.home.HomeRemoteDataSource
import com.rokneltayb.data.dataSource.remote.user.UserRemoteDataSource
import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.address.details.AddressDetailsResponse
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.AddressRepository
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.FavoritesRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(private val remoteSource: AddressRemoteDataSource):
    AddressRepository {
    override suspend fun address(): Result<AddressResponse> = remoteSource.address()
    override suspend fun getAddressById(id:String) = remoteSource.getAddressById(id)

    override suspend fun cities(): Result<CityResponse> = remoteSource.cities()

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
    ): Result<AddAddressResponse> = remoteSource.addAddress(name, phone, cityId, block, street, avenue, buildingNum, floorNum, apartment, address)

    override suspend fun updateAddress(id:String,name:String,
                              phone:String,
                              cityId:String,
                              block:String,
                              street:String,
                              avenue:String,
                              buildingNum:String,
                              floorNum:String,
                              apartment:String,
                              address:String):Result<AddressDetailsResponse> = remoteSource.updateAddress(id, name, phone, cityId, block, street, avenue, buildingNum, floorNum, apartment, address)

    override suspend fun deleteAddress(id: Int): Result<DeleteAddressResponse> = remoteSource.deleteAddress(id)


}