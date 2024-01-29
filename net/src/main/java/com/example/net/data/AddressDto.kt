package com.example.net.data

import com.example.domain.data.Address
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class AddressResponse(
    @SerialName("suggestions")
    val addresses: List<AddressDto>
)
@Serializable
data class AddressDto(
    @SerialName("value")
    val address: String
)

internal fun AddressDto.toAddress() = Address(address)

@Serializable
data class AddressRequest(
    @SerialName("query")
    val query: String,
    val division : String = "administrative",
    val limit: Int = 5,
    val locale: String = "ru",
)