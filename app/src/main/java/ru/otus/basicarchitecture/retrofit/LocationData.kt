package ru.otus.basicarchitecture.retrofit

import com.squareup.moshi.Json

data class LocationData(
    @field:Json(name = "suggestions")
    val suggestions: List<LocationValue>
)

data class LocationValue(
    @field:Json(name = "value")
    val value: String
)

data class LocationQuery(
    @field:Json(name = "query")
    val query: String
)