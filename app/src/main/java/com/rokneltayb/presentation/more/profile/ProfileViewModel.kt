package com.rokneltayb.presentation.more.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.home.home.HomeResponse
import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.profile.ProfileResponse
import com.rokneltayb.data.model.login.profile.update.UpdateProfileResponse
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
class ProfileViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun profile() {
        viewModelScope.launch {
            when (val result = useCase.profile()) {
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

    fun updateProfile(name:String,phone:String,email:String) {
        viewModelScope.launch {
            when (val result = useCase.updateProfile(name, phone, email)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.UpdateSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun changePassword(oldPassword:String,newPassword:String,confirmNewPassword:String) {
        viewModelScope.launch {
            when (val result = useCase.changePassword(oldPassword,newPassword,confirmNewPassword)) {
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

    fun deleteAccout() {
        viewModelScope.launch {
            when (val result = useCase.deleteAccount()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.DeleteSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun logout() {
        viewModelScope.launch {
            when (val result = useCase.logout()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.LogoutSuccess(result.data!!)
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
        class Success(val data: ProfileResponse) : UiState()
        class DeleteSuccess(val data: DeleteAccountResponse) : UiState()
        class LogoutSuccess(val data: LogoutResponse) : UiState()
        class UpdateSuccess(val data: UpdateProfileResponse) : UiState()
        class ChangePasswordSuccess(val data: ChangePasswordResponse) : UiState()
    }
}