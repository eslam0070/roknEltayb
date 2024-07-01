package com.rokneltayb.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.cart.CartResponse
import com.rokneltayb.data.model.cart.DateResponse
import com.rokneltayb.data.model.cart.add.AddCartResponse
import com.rokneltayb.data.model.cart.coupon.AddCouponResponse
import com.rokneltayb.data.model.cart.delete.DeleteCartResponse
import com.rokneltayb.data.model.cart.delivery.DeliveryimesResponse
import com.rokneltayb.data.model.cart.update.UpdateCartResponse
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

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun getCart() {
        viewModelScope.launch {
            when (val result = useCase.getCart()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.GetCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }


    fun addCard(productId: String, shapeId: String, count: String) {
        viewModelScope.launch {
            when (val result = useCase.addCart(productId, shapeId, count)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.AddCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun updateCart(productId: String, shapeId: String, count: String) {
        viewModelScope.launch {
            when (val result = useCase.updateCart(productId, shapeId, count)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.UpdateCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun deleteCard(productId: String, shapeId: String) {
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


    fun applyCouponCart(coupon: String) {
        viewModelScope.launch {
            when (val result = useCase.applyCouponCart(coupon)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.AddCouponCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }


    fun deleteCouponCart() {
        viewModelScope.launch {
            when (val result = useCase.deleteCouponCart()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.DeleteCouponCartSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun deliveryTimes() {
        viewModelScope.launch {
            when (val result = useCase.deliveryTimes()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.GetDeliveryTimeSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun deliveryDates() {
        viewModelScope.launch {
            when (val result = useCase.deliveryDates()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.GetDeliveryDateSuccess(result.data!!)
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
        class GetCartSuccess(val data: CartResponse) : UiState()
        class AddCartSuccess(val data: AddCartResponse) : UiState()
        class UpdateCartSuccess(val data: UpdateCartResponse) : UiState()
        class DeleteCartSuccess(val data: DeleteCartResponse) : UiState()
        class AddCouponCartSuccess(val data: AddCouponResponse) : UiState()
        class DeleteCouponCartSuccess(val data: AddCouponResponse) : UiState()
        class GetDeliveryTimeSuccess(val data: DeliveryimesResponse) : UiState()
        class GetDeliveryDateSuccess(val data: DateResponse) : UiState()
    }
}