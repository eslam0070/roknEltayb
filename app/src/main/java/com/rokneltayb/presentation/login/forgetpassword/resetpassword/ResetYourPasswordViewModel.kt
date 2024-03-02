package com.rokneltayb.presentation.login.forgetpassword.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.UserUseCase
import com.rokneltayb.presentation.login.forgetpassword.ForgetPasswordViewModel
import com.rokneltayb.presentation.more.profile.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetYourPasswordViewModel@Inject constructor(private val useCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun resetPassword(phone: String,otp:String) {
        viewModelScope.launch {
            when (val result = useCase.resetPassword(phone,otp)) {
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

    fun changePassword(newPassword:String,confirmNewPassword:String) {
        viewModelScope.launch {
            when (val result = useCase.changePassword("",newPassword,confirmNewPassword)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.ChangePasswordSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun removeState(){_uiState.value = UiState.Idle }

    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()
        class Error(val errorData: ErrorResponse): UiState()
        class Success(val data: ResetPasswordResponse) : UiState()
        class ChangePasswordSuccess(val data: ChangePasswordResponse) : UiState()

    }
}