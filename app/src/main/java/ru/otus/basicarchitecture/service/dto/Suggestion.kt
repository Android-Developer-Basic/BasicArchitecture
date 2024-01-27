package ru.otus.basicarchitecture.service.dto

import kotlinx.serialization.Serializable

@Serializable
data class Suggestion(
    val value: String?
)
