package com.example.domain

import com.example.domain.data.Address

interface DaDataRepository {
    suspend fun getAddressSuggestions(query: String): List<Address>
}