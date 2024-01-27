package com.example.net

import com.example.domain.DaDataRepository
import com.example.domain.data.Address
import com.example.net.data.toAddress
import java.io.IOException

class DaDataRepositoryImpl internal constructor(private val daDataApi: DaDataApi) :
    DaDataRepository {

    override suspend fun getAddressSuggestions(query: String): List<Address> {
        val response = daDataApi.getAddress(query)

        if (!response.isSuccessful) {
            throw IOException("Error getting address suggestions: ${response.message()}")
        }

        return response.body()?.map { it.toAddress() } ?: emptyList()
    }
}
