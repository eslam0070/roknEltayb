package com.rokneltayb.presentation.more.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import com.rokneltayb.data.model.products.details.ProductDetailsResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.FavoritesUseCase
import com.rokneltayb.domain.usecase.HomeUseCase
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val useCase: FavoritesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun favorites() {
        viewModelScope.launch {
            when (val result = useCase.favorites()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.FavoriteSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun storeFavorite(productId:Int) {
        viewModelScope.launch {
            when (val result = useCase.storeFavorites(productId)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.StoreFavoriteSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun deleteFavorite(productId:Int) {
        viewModelScope.launch {
            when (val result = useCase.deleteFavorites(productId)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.DeleteFavoriteSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun removeState() {
        _uiState.value = UiState.Idle
    }


    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()
        class Error(val errorData: ErrorResponse): UiState()
        class FavoriteSuccess(val data: FavoritesResponse) : UiState()
        class StoreFavoriteSuccess(val data: AddFavoritesResponse) : UiState()
        class DeleteFavoriteSuccess(val data: DeleteFavoritesResponse) : UiState()
    }
}