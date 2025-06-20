package com.rokneltayb.domain.entity

import com.rokneltayb.domain.entity.ErrorType

interface ErrorTypeHandler {
    fun getError(exception: Exception): ErrorType
}