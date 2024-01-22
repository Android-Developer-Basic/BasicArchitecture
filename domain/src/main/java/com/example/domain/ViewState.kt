package com.example.domain

sealed class ViewState<out T> {
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val exception: Throwable) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}