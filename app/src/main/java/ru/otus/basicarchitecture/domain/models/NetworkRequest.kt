package ru.otus.basicarchitecture.domain.models

data class NetworkRequest(
    var count: Int = 5,
    var query: String = ""
)