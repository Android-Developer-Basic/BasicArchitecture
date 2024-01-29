package com.example.net

import android.util.Log
import com.example.domain.DaDataRepository
import com.example.domain.data.Address
import com.example.net.data.AddressRequest
import com.example.net.data.toAddress
import java.io.IOException
import javax.inject.Inject

class DaDataRepositoryImpl @Inject constructor(private val daDataApi: DaDataApi) :
    DaDataRepository {

    override suspend fun getAddressSuggestions(query: String): List<Address> {


        val response = daDataApi.getAddress(AddressRequest(query))
        Log.d("DaDataRepositoryImpl", "Response: $response")
        Log.d("DaDataRepositoryImpl", "Query: $query")

        if (!response.isSuccessful) {
            throw IOException("Error getting address suggestions: ${response.message()}")
        }

        return response.body()?.addresses?.map { it.toAddress() } ?: emptyList()
    }
}
