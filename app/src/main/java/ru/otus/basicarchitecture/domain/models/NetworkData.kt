package ru.otus.basicarchitecture.domain.models

data class Suggestions(
    val suggestions: List<Address>
)



data class Address(


    val value: String,

    val unrestricted_value: String,

    )