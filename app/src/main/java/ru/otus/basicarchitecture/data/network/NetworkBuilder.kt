package ru.otus.basicarchitecture.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkBuilder(private val url: String) {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun okHttpClient() = OkHttpClient.Builder().addInterceptor{
            chain ->
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Token $TOKEN")
            .build()
        return@addInterceptor chain.proceed(request)
    }

    val service: NetworkService by lazy { retrofit.create(NetworkService::class.java) }

    companion object{
        const val BASE_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/"
        const val TOKEN = "ecd14fdba75f3492feb103412d50e01ae1dd9825"
    }



}