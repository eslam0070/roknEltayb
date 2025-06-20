package com.rokneltayb.data.network


import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.categories.CategoriesResponse
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.SortedMap

interface HomeServices {
    @GET("client/home")
    suspend fun home(): Response<HomeResponse>

    @GET("client/categories")
    suspend fun categories(): Response<CategoriesResponse>
    @GET("client/products")
    suspend fun products(@Query("page") page:Int,@Query("category_id")categoryId:String,@Query("sort")sort: String,@Query("search")search:String,@Query("type")type:String): Response<ProductsResponse>

    @GET("client/products")
    suspend fun searchOnProducts(@Query("search")search:String): Response<ProductsResponse>

    @GET("client/products")
    suspend fun prorudtsWithOutSearch(): Response<ProductsResponse>

    @GET("client/product-details")
    suspend fun productDetails(@Query("product_id")productId:Int): Response<ProductDetailsResponse>

    @GET("client/cart")
    suspend fun getCart2():Response<CartResponse>

    @Multipart
    @POST("client/apply-coupon")
    suspend fun applyCouponCart2(@Part("coupon") coupon: RequestBody): Response<AddCouponResponse>

}