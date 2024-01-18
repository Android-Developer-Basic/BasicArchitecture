package ru.otus.basicarchitecture.address

import com.google.gson.annotations.SerializedName

class AddressDataModel() {

    data class MineUserInfo(
        @SerializedName("suggestions")
        val suggestions: Data
    )

    data class Data(
        @SerializedName("value")
        val value: String,
        @SerializedName("unrestricted_value")
        val unrestrictedValue: String,
    )
}