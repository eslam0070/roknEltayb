package com.rokneltayb.domain.usecase

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
import com.rokneltayb.data.model.notification_count.NotificationCountResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.repository.CartRepository
import com.rokneltayb.domain.repository.FavoritesRepository
import com.rokneltayb.domain.repository.HomeRepository
import com.rokneltayb.domain.repository.UserRepository
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(private val repo: FavoritesRepository) {
    suspend fun favorites(): Result<FavoritesResponse> = repo.favorites()
    suspend fun notificationCount(): Result<NotificationCountResponse> = repo.notificationCount()
    suspend fun storeFavorites(productId: Int): Result<AddFavoritesResponse> = repo.storeFavorites(productId)
    suspend fun deleteFavorites(productId: Int): Result<DeleteFavoritesResponse> = repo.deleteFavorites(productId)
}