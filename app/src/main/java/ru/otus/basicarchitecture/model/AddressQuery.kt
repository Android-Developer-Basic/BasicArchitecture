package ru.otus.basicarchitecture.model

import com.squareup.moshi.Json

data class AddressQuery(
    @field:Json(name="query")
    val query:String)
