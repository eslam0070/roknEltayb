package com.rokneltayb.presentation.categories.products

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokneltayb.data.model.products.DataXX
import com.rokneltayb.data.model.products.ProductsResponse
import com.rokneltayb.domain.entity.ErrorResponse
import com.rokneltayb.domain.entity.Result
import com.rokneltayb.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun products(page:Int,categoryId: String, sort:String, search:String, type:String) {
        viewModelScope.launch {
            when (val result = useCase.products(page,categoryId, sort, search,type)) {
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
        class Error(val errorData: ErrorResponse): UiState()
        class Success(val data: ProductsResponse) : UiState()
    }
}