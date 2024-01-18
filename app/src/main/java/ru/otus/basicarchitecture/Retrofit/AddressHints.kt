package ru.otus.basicarchitecture.Retrofit

import retrofit2.http.GET
import ru.otus.basicarchitecture.address.AddressDataModel

interface AddressHints {
    @GET()
    suspend fun getAddress(): List<AddressDataModel>
}