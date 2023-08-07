package ru.otus.basicarchitecture.domain.usecases.network_use_cases

import kotlinx.coroutines.flow.Flow
import ru.otus.basicarchitecture.domain.models.NetworkResult
import ru.otus.basicarchitecture.domain.repository.NetworkRepository

class GetNetworkResponseUseCase(private val networkRepository: NetworkRepository) {

    fun getResponse(): Flow<NetworkResult<Any>> {
        return networkRepository.getResponse()
    }
}