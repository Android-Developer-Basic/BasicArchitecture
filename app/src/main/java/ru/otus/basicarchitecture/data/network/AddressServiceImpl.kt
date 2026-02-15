package ru.otus.basicarchitecture.data.network

import ru.otus.basicarchitecture.domain.AddressService
import javax.inject.Inject

class AddressServiceImpl @Inject constructor(private val addressApi: AddressApi) : AddressService {
    override suspend fun getHints(query: AddressQuery): AddressResponse {
        return addressApi.getHints(query = query)
    }
}