package com.rokneltayb.presentation.cart.checkout

import androidx.lifecycle.ViewModel
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.usecase.CartUseCase
import com.rokneltayb.presentation.cart.CartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(private val useCase: CartUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<CartViewModel.UiState>(CartViewModel.UiState.Idle)
    val uiState: StateFlow<CartViewModel.UiState>
        get() = _uiState

    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()
        class Error(val errorData: ErrorResponse): UiState()
        class GetCartSuccess(val data: DeleteCartResponse) : UiState()
    }
}