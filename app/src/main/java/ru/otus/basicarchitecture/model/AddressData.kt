package ru.otus.basicarchitecture.model

import com.squareup.moshi.Json

data class AddressData(
    @field:Json(name="suggestions")
    val suggestions:List<AddressValue>)

data class AddressValue(
    @field:Json(name="value")
    val value:String)
