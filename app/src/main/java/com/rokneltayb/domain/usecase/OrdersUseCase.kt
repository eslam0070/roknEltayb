package com.rokneltayb.domain.usecase

import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
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
import com.rokneltayb.data.model.orders.OrdersResponse
import com.rokneltayb.data.model.orders.add.AddOrderResponse
import com.rokneltayb.data.model.orders.details.OrderDetailsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.AddressRepository
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.FavoritesRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.OrdersRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class OrdersUseCase @Inject constructor(private val repo: OrdersRepository) {
    suspend fun getOrders(): Result<OrdersResponse> = repo.getOrders()
    suspend fun getOrderDetails(orderId:Int): Result<OrderDetailsResponse> = repo.getOrderDetails(orderId)
    suspend fun addOrder(addressId:Int,deliveryTimeId:Int):Result<AddOrderResponse> = repo.addOrder(addressId,deliveryTimeId)
}