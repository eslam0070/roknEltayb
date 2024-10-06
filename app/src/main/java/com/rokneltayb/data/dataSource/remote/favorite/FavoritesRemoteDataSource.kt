package com.rokneltayb.data.dataSource.remote.favorite

import com.rokneltayb.data.model.StringMessageResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import com.rokneltayb.data.model.notification_count.NotificationCountResponse
import com.rokneltayb.domain.entity.Result

interface FavoritesRemoteDataSource {

    suspend fun favorites(): Result<FavoritesResponse>
    suspend fun notificationCount(): Result<NotificationCountResponse>
    suspend fun storeFavorites(productId: Int): Result<AddFavoritesResponse>
    suspend fun deleteFavorites(productId: Int): Result<DeleteFavoritesResponse>
}