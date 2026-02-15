package ru.otus.basicarchitecture.data.network

import kotlinx.serialization.Serializable

@Serializable
data class AddressQuery(
    val query: String
)