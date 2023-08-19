package ru.otus.basicarchitecture

import com.google.gson.annotations.SerializedName

data class Suggestion(
    val value: String,
    @SerializedName("unrestricted_value")
    val unrestrictedValue: String,
    val data: Data,
)

data class Data(
    @SerializedName("region_kladr_id")
    val regionKladrId: String,
    @SerializedName("city_kladr_id")
    val cityKladrId: String,
    @SerializedName("kladr_id")
    val kladrId: String,
    @SerializedName("geo_lat")
    val geoLat: String,
    @SerializedName("geo_lon")
    val geoLon: String,
)
