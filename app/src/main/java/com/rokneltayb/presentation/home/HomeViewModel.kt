package com.rokneltayb.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.HomeUseCase
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {

        private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
        val uiState: StateFlow<UiState>
        get() = _uiState

        fun home() {
            viewModelScope.launch {
                when (val result = useCase.home()) {
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
            class Success(val data: HomeResponse) : UiState()
        }
}