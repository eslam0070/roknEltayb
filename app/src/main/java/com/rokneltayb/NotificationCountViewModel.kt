package com.rokneltayb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.notification_count.NotificationCountResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.FavoritesUseCase
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class NotificationCountViewModel @Inject constructor(private val useCase: FavoritesUseCase) : ViewModel() {

        private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
        val uiState: StateFlow<UiState>
            get() = _uiState

        fun notificationCount() {
            viewModelScope.launch {
                when (val result = useCase.notificationCount()) {
                    is Result.Error -> {
                        _uiState.value = UiState.Error(result.errorType!!)
                    }

                    is Result.Success -> {
                        _uiState.value = UiState.Success(result.data!!)
                    }

                    is Result.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                }
            }
        }



        sealed class UiState {
            data object Loading : UiState()
            class Error(val errorData: ErrorResponse): UiState()
            class Success(val data: NotificationCountResponse) : UiState()
        }
    }