package com.rokneltayb.presentation.login.forgetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.signup.SignUpResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rokneltayb.domain.entity.Result

@HiltViewModel
class ForgetPasswordViewModel  @Inject constructor(private val useCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun sendOtp(phone: String) {
        viewModelScope.launch {
            when (val result = useCase.sendOTP(phone)) {
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
        data object Idle : UiState()
        class Error(val errorData: ErrorResponse): UiState()
        class Success(val data: SignUpResponse) : UiState()
    }
}