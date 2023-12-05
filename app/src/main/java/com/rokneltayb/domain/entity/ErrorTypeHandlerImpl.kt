package com.rokneltayb.domain.entity

import com.exas.crm.domain.entity.ErrorTypeHandler
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorTypeHandlerImpl @Inject constructor() : ErrorTypeHandler {
    override fun getError(exception: Exception): ErrorType {
        return when (exception) {
            is UnknownHostException -> {
                ErrorType.NetworkError
            }
            else -> {
                ErrorType.UnknownError
            }
        }
    }
}