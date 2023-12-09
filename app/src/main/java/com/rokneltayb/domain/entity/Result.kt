package com.rokneltayb.domain.entity

sealed class Result<T>(val data: T?,val errorType:ErrorResponse?) {
    class Success<T>(data: T) : Result<T>(data,null)
    class Loading<T>() : Result<T>(null, null)
    class Error<T>(message: ErrorResponse?) : Result<T>(null,message)
}