package ru.otus.basicarchitecture.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressQuery(
    @SerialName("query")
    val query: String
)