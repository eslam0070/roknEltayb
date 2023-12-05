package com.rokneltayb.domain.entity

sealed class Result2<T>(val data: T?,val errorType:ErrorResponse?) {
    class Success<T>(data: T) : Result2<T>(data,null)
    class Loading<T>() : Result2<T>(null, null)
    class Error<T>(message: ErrorResponse?) : Result2<T>(null,message)
}