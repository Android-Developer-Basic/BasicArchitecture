package ru.otus.basicarchitecture.internetAdrdess

import com.google.gson.annotations.SerializedName


data class Address(   //компонент ответа
    val value: String,
    @SerializedName("unrestricted_value")         //придет с сайта под этим именем
    val unrestrictedValue: String,
)
