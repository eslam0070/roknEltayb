package com.rokneltayb.domain.entity

sealed class ErrorType {
    object NetworkError : ErrorType()
    object ServerError : ErrorType()
    object UnknownError : ErrorType()
    object DataError : ErrorType()
}