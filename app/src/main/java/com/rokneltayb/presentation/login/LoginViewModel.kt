package com.rokneltayb.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result2
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun login( fcmToken: String,phone: String, password: String) {
        viewModelScope.launch {
            when (val result = useCase.login(fcmToken,phone, password)) {
                is Result2.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result2.Success -> {
                    _uiState.value = UiState.Success(result.data!!)
                }

                is Result2.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }

    }

    sealed class UiState {
        data object Loading : UiState()
        class Error(val errorData:ErrorResponse): UiState()
        class Success(val data: LoginResponse) : UiState()
    }
}