package com.rokneltayb.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        fcmToken: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result =
                useCase.signUp(name, phone, email, password, passwordConfirmation, fcmToken)
            _uiState.value = when (result) {
                is Result.Loading -> UiState.Loading
                is Result.Error -> {
                    UiState.Error(result.errorType.toString())

                }

                is Result.Success -> {

                }

            }
        }
    }


    fun removeState(){
        _uiState.value = UiState.Idle
    }

    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()
        class Error(val errorData: ErrorResponse): UiState()
        class Success(val data: SignUpResponse) : UiState()
    }
}