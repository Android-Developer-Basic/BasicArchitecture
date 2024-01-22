package com.example.net.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("value")
    val address: String
)

data class DaDataQuery(
   val query: String,
   val division: String = "administrative"
)