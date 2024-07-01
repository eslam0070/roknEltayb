package com.rokneltayb.data.network


import com.rokneltayb.data.model.StringMessageResponse
import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.cart.delivery.DeliveryTime
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.orders.OrdersResponse
import com.rokneltayb.data.model.orders.add.AddOrderResponse
import com.rokneltayb.data.model.orders.details.OrderDetailsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OrdersServices {

    @GET("client/orders")
    suspend fun getOrders(): Response<OrdersResponse>
    @GET("client/orders/details")
    suspend fun getOrderDetails(@Query("order_id")orderId:Int): Response<OrderDetailsResponse>
    @FormUrlEncoded
    @POST("client/store-order")
    suspend fun addOrder(@Field("address_id")addressId:Int,@Field("delivery_date")delivery_date: String,@Field("delivery_time_id")deliveryTimeId: Int):Response<AddOrderResponse>
}