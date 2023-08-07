package ru.otus.basicarchitecture.domain.models

sealed interface NetworkResult<T>

class NetworkSuccess<D>(val networkData: D): NetworkResult<D>

class NetworkError<E>(val errorCode: Int, val errorMessage: String): NetworkResult<E>

class NetworkException<E>(val exception: E ): NetworkResult<E>
