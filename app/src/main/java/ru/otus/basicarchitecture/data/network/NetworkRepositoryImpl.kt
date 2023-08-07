package ru.otus.basicarchitecture.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.otus.basicarchitecture.domain.models.NetworkRequest
import ru.otus.basicarchitecture.domain.models.NetworkResult
import ru.otus.basicarchitecture.domain.repository.NetworkRepository

class NetworkRepositoryImpl: NetworkRepository {
    override fun sendRequest(request: NetworkRequest) {
        Network.request = request
    }

    override fun getResponse(): Flow<NetworkResult<Any>> {
        return flow {

            emit(Network.sendRequest())

        }
    }
}
