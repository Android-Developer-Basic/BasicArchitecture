package com.example.net.data

import com.example.domain.data.Address
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("value")
    val address: String
)

internal fun AddressDto.toAddress() = Address(address)

data class DaDataQuery(
   val query: String,
   val division: String = "administrative"
)