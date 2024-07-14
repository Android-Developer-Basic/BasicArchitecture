package ru.otus.basicarchitecture.Core.Model

data class Address(
    val country: String,
    val city: String,
    val address: String
) : BaseModel