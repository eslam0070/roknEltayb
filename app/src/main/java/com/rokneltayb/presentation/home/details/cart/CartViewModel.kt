package com.rokneltayb.presentation.home.details.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val useCase: CartUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun cart() {
        viewModelScope.launch {
            when (val result = useCase.getCart()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.CartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun storeCart(productId:String,shapeId:String,count:String) {
        viewModelScope.launch {
            when (val result = useCase.addCart(productId, shapeId, count)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.AddToCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun deleteCart(productId:String,shapeId:String) {
        viewModelScope.launch {
            when (val result = useCase.deleteCart(productId, shapeId)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.DeleteCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun removeState() {
        _uiState.value = UiState.Idle
    }

    sealed class UiState {
        data object Loading : UiState()
        data object Idle : UiState()

        class Error(val errorData: ErrorResponse): UiState()
        class CartSuccess(val data: CartResponse) : UiState()
        class AddToCartSuccess(val data: AddCartResponse) : UiState()
        class DeleteCartSuccess(val data: DeleteCartResponse) : UiState()
    }
}