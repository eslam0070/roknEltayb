package com.rokneltayb.data.network


import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.cart.update.UpdateCartResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CartServices {


    @GET("client/cart")
    suspend fun getCart():Response<CartResponse>

    @FormUrlEncoded
    @POST("client/store-cart")
    suspend fun addCart(
        @Field("product_id") productId: String,
        @Field("shape_id") shapeId: String,
        @Field("count") count: String): Response<AddCartResponse>

    @FormUrlEncoded
    @POST("client/update-cart")
    suspend fun updateCart(
        @Field("product_id") productId: String,
        @Field("shape_id") shapeId: String,
        @Field("count") count: String): Response<UpdateCartResponse>

    @FormUrlEncoded
    @POST("client/delete-cart")
    suspend fun deleteCart(
        @Field("product_id") productId: String,
        @Field("shape_id") shapeId: String): Response<DeleteCartResponse>

    @FormUrlEncoded
    @POST("client/apply-coupon")
    suspend fun applyCouponCart(@Field("coupon") coupon: String): Response<AddCouponResponse>

    @POST("client/remove-coupon")
    suspend fun deleteCouponCart(): Response<AddCouponResponse>
}