package com.crxapplications.morsy.core.helper

sealed class Response<out T> {
    data class SuccessResponse<T>(val value: T) : Response<T>()
    data class ErrorResponse<T>(val message: UiText) : Response<T>()
}