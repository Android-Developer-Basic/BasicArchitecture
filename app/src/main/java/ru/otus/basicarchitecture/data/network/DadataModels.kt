package ru.otus.basicarchitecture.data.network

import com.google.gson.annotations.SerializedName

data class DadataRequest(
    @SerializedName("query") val query: String, @SerializedName("count") val count: Int = 10
)

data class DadataResponse(
    @SerializedName("suggestions") val suggestions: List<DadataSuggestion>
)

data class DadataSuggestion(
    @SerializedName("value") val value: String,
    @SerializedName("unrestricted_value") val unrestrictedValue: String
)