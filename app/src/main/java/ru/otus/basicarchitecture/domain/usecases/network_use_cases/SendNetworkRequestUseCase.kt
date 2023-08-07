package ru.otus.basicarchitecture.domain.usecases.network_use_cases

import ru.otus.basicarchitecture.domain.models.NetworkRequest
import ru.otus.basicarchitecture.domain.repository.NetworkRepository

class SendNetworkRequestUseCase(private val networkRepository: NetworkRepository) {

    fun sendRequest(request: NetworkRequest){
        networkRepository.sendRequest(request)
    }
}