package ru.otus.basicarchitecture.domain

import ru.otus.basicarchitecture.data.network.AddressQuery
import ru.otus.basicarchitecture.data.network.AddressResponse

interface AddressService {
    suspend fun getHints(
        query: AddressQuery
    ): AddressResponse
}