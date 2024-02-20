package com.rokneltayb.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rokneltayb.domain.entity.Result

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun login( fcmToken: String,phone: String, password: String) {
        viewModelScope.launch {
            when (val result = useCase.login(fcmToken,phone, password)) {
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


    fun removeState(){
        _uiState.value = UiState.Idle
    }

    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()
        class Error(val errorData:ErrorResponse): UiState()
        class Success(val data: LoginResponse) : UiState()
    }
}