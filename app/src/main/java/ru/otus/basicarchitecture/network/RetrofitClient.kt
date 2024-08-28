package ru.otus.basicarchitecture.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/"

    val instance: DaDataApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(DaDataApi::class.java)
    }
}