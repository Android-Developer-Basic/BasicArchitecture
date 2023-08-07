package ru.otus.basicarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.otus.basicarchitecture.domain.models.NetworkRequest
import ru.otus.basicarchitecture.domain.models.NetworkResult

interface NetworkRepository {

    fun sendRequest(request: NetworkRequest)

    fun getResponse(): Flow<NetworkResult<Any>>


}