package com.rokneltayb.presentation.more.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.orders.OrdersResponse
import com.rokneltayb.data.model.orders.add.AddOrderResponse
import com.rokneltayb.data.model.orders.details.OrderDetailsResponse
import com.rokneltayb.data.model.settings.SettingsResponse
import com.rokneltayb.data.model.settings.contact.ContactUsResponse
import com.rokneltayb.data.model.settings.pages.PagesResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.OrdersUseCase
import com.rokneltayb.domain.usecase.SettingsUseCase
import com.rokneltayb.presentation.more.contactus.ContactUsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel  @Inject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState


    fun getOrders() {
        viewModelScope.launch {
            when (val result = useCase.getOrders()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.GetAllOrdersSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun getOrderDetails(orderId:Int) {
        viewModelScope.launch {
            when (val result = useCase.getOrderDetails(orderId)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.GetOrderDetailsSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    fun addOrder(addressId:Int,deliveryTimeId:Int) {
        viewModelScope.launch {
            when (val result = useCase.addOrder(addressId,deliveryTimeId)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.AddOrderSuccess(result.data!!)
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
        class GetAllOrdersSuccess(val data: OrdersResponse) : UiState()
        class GetOrderDetailsSuccess(val data: OrderDetailsResponse) : UiState()
        class AddOrderSuccess(val data: AddOrderResponse) : UiState()
    }
}