package com.rokneltayb.domain.repository

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

interface FavoritesRepository {
    suspend fun favorites(): Result<FavoritesResponse>
    suspend fun storeFavorites(productId: Int): Result<AddFavoritesResponse>
    suspend fun deleteFavorites(productId: Int): Result<DeleteFavoritesResponse>
}