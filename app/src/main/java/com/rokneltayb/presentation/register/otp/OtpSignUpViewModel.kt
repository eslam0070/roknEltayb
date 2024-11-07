package com.rokneltayb.presentation.register.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.otp.OtpSignUpResponse
import com.rokneltayb.data.model.otp.ResendOtpSignUpResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpSignUpViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun verifyPhone(phone: String,token:String) {
        viewModelScope.launch {
            when (val result = useCase.verifyPhone(phone,token)) {
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

    fun resendVerifyPhone(phone: String) {
        viewModelScope.launch {
            when (val result = useCase.resendVerifyPhone(phone)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.ResendSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()
        class Error(val errorData: ErrorResponse): UiState()
        class Success(val data: OtpSignUpResponse) : UiState()
        class ResendSuccess(val data: ResendOtpSignUpResponse) : UiState()
    }
}