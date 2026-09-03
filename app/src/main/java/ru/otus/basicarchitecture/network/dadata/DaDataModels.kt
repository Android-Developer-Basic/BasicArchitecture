package ru.otus.basicarchitecture.network.dadata

import com.google.gson.annotations.SerializedName

data class AddressSuggestionsRequest(
    val query: String,
    val count: Int = 10,
    val locations: List<LocationFilter> = listOf(LocationFilter()),
)

data class LocationFilter(
    val country: String = "*",
)

data class AddressSuggestionsResponse(
    val suggestions: List<AddressSuggestionDto>,
)

data class AddressSuggestionDto(
    val value: String,
    @SerializedName("unrestricted_value")
    val unrestrictedValue: String,
    val data: AddressDataDto,
)

data class AddressDataDto(
    val country: String?,
    @SerializedName("city_with_type")
    val cityWithType: String?,
    @SerializedName("settlement_with_type")
    val settlementWithType: String?,
    @SerializedName("street_with_type")
    val streetWithType: String?,
    @SerializedName("house_type")
    val houseType: String?,
    val house: String?,
)