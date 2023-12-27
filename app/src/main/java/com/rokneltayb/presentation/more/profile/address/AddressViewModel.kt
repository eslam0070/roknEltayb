package com.rokneltayb.presentation.more.profile.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
import com.rokneltayb.data.model.login.profile.ProfileResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.AddressUseCase
import com.rokneltayb.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val useCase: AddressUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun address() {
        viewModelScope.launch {
            when (val result = useCase.address()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.AddressSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }

    }

    fun cities() {
        viewModelScope.launch {
            when (val result = useCase.cities()) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.CitiesSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun add( name: String,
             phone: String,
             cityId: String,
             block: String,
             street: String,
             avenue: String,
             buildingNum: String,
             floorNum: String,
             apartment: String,
             address: String) {
        viewModelScope.launch {
            when (val result = useCase.addAddress(name, phone, cityId, block, street, avenue, buildingNum, floorNum, apartment, address)) {
                is Result.Error -> {
                    _uiState.value = UiState.Error(result.errorType!!)
                }

                is Result.Success -> {
                    _uiState.value = UiState.AddSuccess(result.data!!)
                }

                is Result.Loading -> {
                    _uiState.value = UiState.Loading
                }
            }

        }
    }

    fun delete(id:Int) {
        viewModelScope.launch {
            when (val result = useCase.deleteAddress(id)) {
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
    sealed class UiState {
        data object Loading : UiState()
        class Error(val errorData: ErrorResponse) : UiState()
        class AddressSuccess(val data: AddressResponse) : UiState()
        class CitiesSuccess(val data: CityResponse) : UiState()
        class AddSuccess(val data: AddAddressResponse) : UiState()
        class DeleteSuccess(val data: DeleteAddressResponse) : UiState()
    }
}